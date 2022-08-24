package mhmd.salem.coffe.ViewModels

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.colorResource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.storage.FirebaseStorage
import mhmd.salem.coffe.R
import mhmd.salem.coffe.data.UserData

class PersonalInformationViewModel():ViewModel() {


    private var _userLiveData = MutableLiveData<List<UserData>>()
    var userLiveData :LiveData<List<UserData>> = _userLiveData

    private val fireStore     = FirebaseFirestore.getInstance()
    private val user          = FirebaseAuth.getInstance().currentUser!!.uid
    private val db            = FirebaseStorage.getInstance().getReference().child("avatar/*")

    fun getUserInformation()
    {
        fireStore.collection("users").document(user).addSnapshotListener{
                values , error ->
            if (error != null)
            {
                Log.d("testApp" , error.message.toString())
            }
            else
            {
                val userList  = arrayListOf<UserData>()
                val userModel = values?.toObject(UserData::class.java)
                    userList.add(userModel!!)
                _userLiveData.value = userList
            }
        }
    }

    var userName :String ? = null
    fun updateUserInformation(context: Context)
    {
        val userMap         = HashMap<String , Any>()
            userMap["name"] = userName.toString()

        fireStore.collection("users").document(user)
            .update(userMap)
            .addOnSuccessListener {
                Toast.makeText(context, "User name updated success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, ""+it.message.toString(), Toast.LENGTH_LONG).show()
            }
    }

    var  imageUri : Uri ? = null
     suspend fun uploadProfileImage(context: Context)
    {
        if (imageUri != null)
        {
            val imagePath = db.child(user)
                imagePath.putFile(imageUri!!)  // todo save image into firebase storage
                .addOnSuccessListener {
                    imagePath.downloadUrl.addOnSuccessListener { uri ->   // todo get img from db storage
                        val userMap = HashMap<String , Any>()
                            userMap["profileImg"] = uri.toString()

                        fireStore.collection("users").document(user).update(userMap) //todo save this img into firestore
                            .addOnSuccessListener {
                                Toast.makeText(context, "Image updated successfully", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, ""+it.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        }
        else
        {
            Toast.makeText(context, "We don't found this image please select other", Toast.LENGTH_SHORT).show()
        }
    }
}