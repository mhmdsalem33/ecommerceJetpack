package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RestaurantDetailsViewModel () :ViewModel() {

    var id            :String  ? = null
    var productImg    :String  ? = null
    var description   :String  ? = null
    var productName   :String  ? = null
    var productPrice  :Double  ? = null
    var totalPrice    :Double  ? = null
    var totalQuantity :String  ? = null

    fun postRestaurantMealToFirestore(context :Context)
    {
        val cartMap = HashMap<String , Any>()
            cartMap["id"]            = id.toString()
            cartMap["productImg"]    = productImg.toString()
            cartMap["Description"]   = description.toString()
            cartMap["productName"]   = productName.toString()
            cartMap["productPrice"]  = productPrice!!.toDouble()
            cartMap["totalPrice"]    = totalPrice!!.toDouble()
            cartMap["totalQuantity"] = totalQuantity.toString()

        FirebaseFirestore.getInstance().collection("Cart")
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