package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.data.CartData



@Composable
fun PlaceOrderScreen(navController: NavController)
{
    val fireStore = FirebaseFirestore.getInstance()
    val user      = FirebaseAuth.getInstance().currentUser!!.uid

    // todo  receive  all cart data by Serializable
    val data :List<CartData>? = navController.previousBackStackEntry?.arguments?.getSerializable("itemsCart") as ArrayList<CartData>?
    val context = LocalContext.current

    if (data != null)  //todo check data if not empty
    {
        for (i in data) // Find All Cart Information
        {
            val cartMap = HashMap<String , Any>()
            cartMap["id"]               = i.id
            cartMap["productName"]      = i.productName
            cartMap["Description"]      = i.Description
            cartMap["productImg"]       = i.productImg
            cartMap["productPrice"]     = i.productPrice
            cartMap["totalPrice"]       = i.totalPrice
            cartMap["totalQuantity"]    = i.totalQuantity

            fireStore.collection("Orders")
                .document(user)
                .collection("UserOrder")
                .add(cartMap)
                .addOnFailureListener {
                    Toast.makeText(context, ""+it.message.toString(), Toast.LENGTH_SHORT).show()
                }
                .addOnSuccessListener {
                    Toast.makeText(context, "We received your order successfully", Toast.LENGTH_SHORT).show()
                }
        }
    }
    else
    {
        Log.d("testApp" , "data is Empty")
    }
}