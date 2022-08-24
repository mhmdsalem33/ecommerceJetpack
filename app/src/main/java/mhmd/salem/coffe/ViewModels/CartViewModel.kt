package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mhmd.salem.coffe.data.CartData

class CartViewModel() : ViewModel() {

     var cartMutableStateOf by mutableStateOf<List<CartData>>(emptyList())

    private val _cartStateFlow =MutableStateFlow<List<CartData>>(emptyList())
    val cartStateFlow :StateFlow<List<CartData>> = _cartStateFlow

    private val _cartLiveData = MutableLiveData<List<CartData>>()
    val  cartLiveData :LiveData<List<CartData>> = _cartLiveData

    //todo  get values from Cart from cart ++ and --
    var  id             : String  ? = null
    var  productId      : String ? = null
    var  totalPrice     : Double ? = null
    var  totalQuantity  : String ? = null

    fun getCartInformation(){
        FirebaseFirestore.getInstance().collection("Cart")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("AddToCart")
            .addSnapshotListener{ values , error ->

                val cartList   = arrayListOf<CartData>()
                val cartModel  = values?.toObjects(CartData::class.java)
                    cartList.addAll(cartModel!!)

                 cartMutableStateOf   = cartList
                _cartLiveData.value   = cartList
                _cartStateFlow.value  = cartList
            }
    }

    fun updateNewPrice(context: Context)
    {
        val name = HashMap<String , Any>()
            name["totalPrice"]    = totalPrice!!.toDouble()
            name["totalQuantity"] = totalQuantity.toString()

        FirebaseFirestore.getInstance().collection("Cart")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("AddToCart")
            .document(productId.toString())
            .update(name)
            .addOnSuccessListener {
              Log.d("testApp" , "Success")
            }
            .addOnFailureListener { Toast.makeText(context, "Failed "+it.message.toString(), Toast.LENGTH_SHORT).show() }
    }

    private val user = FirebaseAuth.getInstance().currentUser!!.uid
    fun deleteAllInformationFormCart(context: Context){
        //todo Delete All Documents From Cart
        for (i in cartMutableStateOf)
        {
            Log.d("testApp" ,i.id)
            FirebaseFirestore.getInstance().collection("Cart")
                .document(user)
                .collection("AddToCart")
                .document(i.id)
                .delete()
                .addOnFailureListener {
                    Toast.makeText(context, ""+it.message, Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                    Toast.makeText(context, "All products deleted success", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun deleteCurrentItemFromCart(context: Context){
        FirebaseFirestore.getInstance().collection("Cart")
            .document(user)
            .collection("AddToCart")
            .document(id.toString())
            .delete()
            .addOnSuccessListener {
                Toast.makeText(context, "Product delete success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, ""+it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}