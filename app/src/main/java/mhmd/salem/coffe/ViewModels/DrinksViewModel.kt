package mhmd.salem.coffe.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.data.DrinksData

class DrinksViewModel():ViewModel() {

    private val _drinksLiveData = MutableLiveData<List<DrinksData>>()
    val drinksLiveData :LiveData<List<DrinksData>> = _drinksLiveData

   fun getAllDrinks(){
       FirebaseFirestore.getInstance().collection("Drinks").addSnapshotListener{values , error ->
           if (error != null)
           {
               Log.d("testApp" , error.message.toString())
           }
           else
           {
               val drinksList  = arrayListOf<DrinksData>()
               val drinksModel = values?.toObjects(DrinksData::class.java)
                   drinksList.addAll(drinksModel!!)
               _drinksLiveData.value = drinksList
           }
       }
    }
}
