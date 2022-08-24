package mhmd.salem.coffe.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.data.RestaurantsData

class RestaurantsViewModel():ViewModel() {


    private val _restaurantsLiveData = MutableLiveData<List<RestaurantsData>>()
    val restaurantsLiveData :LiveData<List<RestaurantsData>> = _restaurantsLiveData

    fun getAllRestaurants(){
        FirebaseFirestore.getInstance().collection("Restaurants").addSnapshotListener{ values , error ->
            if (error != null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
                val  restaurantsList  = arrayListOf<RestaurantsData>()
                val  restaurantsModel  = values?.toObjects(RestaurantsData::class.java)
                     restaurantsList.addAll(restaurantsModel!!)
                _restaurantsLiveData.value =  restaurantsList
            }
        }
    }
}