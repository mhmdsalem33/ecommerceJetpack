package mhmd.salem.coffe.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.LoginScreens.Navigation

class SplashScreenActivity : ComponentActivity() {

     lateinit var firebaseAuth : FirebaseAuth
     lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth        = FirebaseAuth.getInstance()
        firebaseFirestore   = FirebaseFirestore.getInstance()

        if (firebaseAuth.currentUser != null){
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            Log.d("testApp" , "User Not Loged in")
        }

        setContent {
                Navigation()
        }
    }
}

