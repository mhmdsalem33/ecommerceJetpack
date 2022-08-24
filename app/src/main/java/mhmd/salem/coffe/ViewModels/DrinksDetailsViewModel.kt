package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.Screen
import mhmd.salem.coffe.Room.DrinksDatabase
import mhmd.salem.coffe.data.DrinksData
import mhmd.salem.coffe.data.WellnessTaskData


class DrinksDetailsViewModel(
    val db : DrinksDatabase
) :ViewModel()
{

    private val fireStore = FirebaseFirestore.getInstance()
    private val user      = FirebaseAuth.getInstance().currentUser!!.uid

    var firstName   : String  ? = null
    var secondName  : String  ? = null
    var imgUrl      : String  ? = null
    var description : String  ? = null
    var price       : String  ? = null
    var quantity    : String  ? = null

    fun sendCartDetailsToFirestore(navController : NavController , context :Context){
        val uniqueId = FirebaseFirestore.getInstance().collection("Cart").document().id

        val cartMap = HashMap<String, Any>()
            cartMap["id"] = uniqueId
            cartMap["productName"]    = "$firstName $secondName"
            cartMap["productImg"]     =  imgUrl.toString()
            cartMap["Description"]    =  description.toString()
            cartMap["productPrice"]   =  price!!.toDouble()
            cartMap["totalPrice"]     =  price!!.toDouble() * quantity!!.toInt()
            cartMap["totalQuantity"]  =  quantity.toString()

        fireStore.collection("Cart")
            .document(user)
            .collection("AddToCart")
            .document(uniqueId)
            .set(cartMap)
            .addOnCompleteListener {
                it.addOnSuccessListener {
                   Toast.makeText(context, "done", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screen.Home.route)
                    {
                        navController.popBackStack()
                    }
                }
                it.addOnFailureListener { error ->
                  Toast.makeText(context, "" + error.message, Toast.LENGTH_SHORT).show()
                }
            }
}

    // Room
    val getAllSavedDrinks = db.drinksDao.getAllDrinksSaved()

        // todo delete all saved in room
        fun clear()
        {
            viewModelScope.launch {
                db.drinksDao.clear()
            }
        }

    fun upsertDrinks(drinks: ArrayList<DrinksData>)
    {
            viewModelScope.launch {
                db.drinksDao.upsert(drinks)
            }
    }

    fun deleteDrinks(drinks: DrinksData)
    {
        viewModelScope.launch {
            db.drinksDao.delete(drinks)
        }
    }
}
