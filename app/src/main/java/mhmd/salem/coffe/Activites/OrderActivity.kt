package mhmd.salem.coffe.Activites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


import mhmd.salem.coffe.R
import mhmd.salem.coffe.data.CartData
import mhmd.salem.coffe.ui.theme.Typography

class OrderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                
                val (imgRef , textRef, buttonRef) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.delivery),
                    contentDescription = "Delivery Img" ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(imgRef)
                        {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                Text(
                    text = "We received your order come back again",
                    fontSize = 18.sp,
                    fontFamily = Typography.h1.fontFamily,
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .constrainAs(textRef)
                        {
                            top.linkTo(imgRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    textAlign = TextAlign.Center
                )

                Button(onClick = {
                    val intent = Intent(context , MainActivity::class.java)
                        startActivity(intent)
                },
                    modifier = Modifier
                        .padding(top = 100.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .constrainAs(buttonRef)
                        {
                            top.linkTo(textRef.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors =  ButtonDefaults.buttonColors(
                        backgroundColor = Yellow500,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Back")
                }
            }
            GetCartInformation()
        }
    }

@Composable
fun GetCartInformation()
{
    val fireStore = FirebaseFirestore.getInstance()
    val user = FirebaseAuth.getInstance().currentUser!!.uid
    val list : List<CartData>? =  intent.getSerializableExtra("cartList") as ArrayList<CartData>?

    val context = LocalContext.current

    if (list != null)  //todo check data if not empty
    {
        for (i in list) // Find All Cart Information
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
                   // Toast.makeText(context, "We received your order successfully", Toast.LENGTH_SHORT).show()
                }

            Toast.makeText(context, "We received your order successfully", Toast.LENGTH_SHORT).show()
        }
    }
    else
    {
        Log.d("testApp" , "data is Empty")
    }
 }
}