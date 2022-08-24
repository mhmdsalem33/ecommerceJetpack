package mhmd.salem.coffe.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.data.MealData

class PopularViewModel(): ViewModel() {

    private val _popularLiveData = MutableLiveData<List<MealData>>()
    val popularLiveData : LiveData<List<MealData>> = _popularLiveData
    fun getPopularItems(){
        FirebaseFirestore.getInstance().collection("Popular").addSnapshotListener{ values , error ->
            if (error != null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
                val popularList  = arrayListOf<MealData>()
                val popularModel = values?.toObjects(MealData::class.java)
                    popularList.addAll(popularModel!!)
                popularList.let {
                    _popularLiveData.postValue(it)
                }
            }
        }
    }
}