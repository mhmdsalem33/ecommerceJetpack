package mhmd.salem.coffe.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.data.CategoryData

class CategoriesViewModel() :ViewModel() {

    private val _categoryLiveData = MutableLiveData<List<CategoryData>>()
    val categoryLiveData :LiveData<List<CategoryData>> = _categoryLiveData

    fun getCategoryLiveData(){
        FirebaseFirestore.getInstance().collection("Categories")
            .addSnapshotListener{  values ,error->
                if (error != null)
                {
                    Log.d("testApp" , error.message.toString())
                }
                else
                {
                    val categoryList   = arrayListOf<CategoryData>()
                    val categoryModel  = values?.toObjects(CategoryData::class.java)
                        categoryList.addAll(categoryModel!!)

                    _categoryLiveData.value = categoryList
                }
        }
    }
}