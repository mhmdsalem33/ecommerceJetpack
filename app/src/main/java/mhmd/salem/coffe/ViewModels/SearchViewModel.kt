package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mhmd.salem.coffe.data.HamburgerData

class SearchViewModel():ViewModel() {

    private val _searchStateFlow = MutableStateFlow<List<HamburgerData>>(emptyList())
    val searchStateFlow :StateFlow<List<HamburgerData>> = _searchStateFlow

     fun searchInFirestore(searchText: String , context :Context) {
        FirebaseFirestore.getInstance().collection("Hamburger")
            .orderBy("searchName")
            .startAt(searchText)
            .endAt("$searchText\uf8ff")
            .limit(10)
            .get()
            .addOnCompleteListener {
                val searchModel  = it.result.toObjects(HamburgerData::class.java)
                    _searchStateFlow.value = searchModel

            }
            .addOnFailureListener{
                Toast.makeText(context, ""+it.message, Toast.LENGTH_SHORT).show()
            }

    }
}