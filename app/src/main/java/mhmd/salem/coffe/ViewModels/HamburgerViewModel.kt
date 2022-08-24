package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import mhmd.salem.coffe.data.HamburgerData

class HamburgerViewModel():ViewModel() {


    private val _hamburgerLiveData = MutableLiveData<List<HamburgerData>>()
    val hamburgerLiveData :LiveData<List<HamburgerData>> = _hamburgerLiveData
    private val fireStore  = FirebaseFirestore.getInstance()

    fun getALlHamburger()
    {
        FirebaseFirestore.getInstance().collection("Hamburger").addSnapshotListener{ values , error ->
            if (error != null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
                val hamburgerList   = arrayListOf<HamburgerData>()
                val hamburgerModel  = values?.toObjects(HamburgerData::class.java)
                    hamburgerList.addAll(hamburgerModel!!)

                hamburgerList.let {
                    _hamburgerLiveData.postValue(it)
                }
            }
        }
    }

    var id            :String  ? = null
    var productImg    :String  ? = null
    var description   :String  ? = null
    var productName   :String  ? = null
    var productPrice  :Double  ? = null
    var totalPrice    :Double  ? = null
    var totalQuantity :String  ? = null

    fun postHamburgerMealToCart(context : Context)
    {
        Log.d("testApp" , id.toString())
        val cartMap = HashMap<String , Any>()
        cartMap["id"]            = id.toString()
        cartMap["productImg"]    = productImg.toString()
        cartMap["Description"]   = description.toString()
        cartMap["productName"]   = productName.toString()
        cartMap["productPrice"]  = productPrice!!.toDouble()
        cartMap["totalPrice"]    = totalPrice!!.toDouble()
        cartMap["totalQuantity"] = totalQuantity.toString()

        fireStore.collection("Cart")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("AddToCart")
            .document(id.toString())
            .set(cartMap)
            .addOnSuccessListener {
                Toast.makeText(context, "Meal Added Successfully", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, ""+it.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }
}