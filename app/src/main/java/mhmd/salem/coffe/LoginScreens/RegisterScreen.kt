package mhmd.salem.coffe.LoginScreens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mhmd.salem.coffe.Activites.MainActivity
import mhmd.salem.coffe.R



@Composable
fun Register(navController :NavController){



    val activity = (LocalContext.current as? Activity)

    val context  = LocalContext.current

    ConstraintLayout(modifier = Modifier
        .background(Color.White)
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {

        val (imageRef  , rowName,  email , password , register)  = createRefs()

            Image(
                painter = painterResource(id = R.drawable.cofee2),
                contentDescription = "" ,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(imageRef) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

        val firstName  = remember{mutableStateOf("")}
        val lastName  = remember{ mutableStateOf("")}
        Row(modifier =
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp)
            .constrainAs(rowName) {
                top.linkTo(imageRef.bottom)
                start.linkTo(imageRef.start)
                end.linkTo(imageRef.end)
            }
            .padding(10.dp)
            
        ) {
            OutlinedTextField(
                value         =   firstName.value ,
                onValueChange = { firstName.value = it },
                modifier =
                Modifier
                    .padding(top = 5.dp)
                    .weight(1f),
                label = { Text(text = stringResource(id = R.string.firstName))},
                placeholder = { Text(text = "Mohamed")},
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor              = Color(108, 99, 255),
                    cursorColor            = Color(108, 99, 255),
                    leadingIconColor       = Color(108, 99, 255),
                    focusedBorderColor     = Color(108, 99, 255),
                    unfocusedBorderColor   = Color(108, 99, 255),
                    focusedLabelColor      = Color(108, 99, 255),
                    unfocusedLabelColor    = Color(108, 99, 255),
                    placeholderColor       = Color(108, 99, 255),
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.size(5.dp))  // Space between textFiled

            OutlinedTextField(
                value         =   lastName.value ,
                onValueChange = { lastName.value = it },
                modifier      =
                Modifier
                    .padding(top = 5.dp)
                    .weight(1f),
                label = { Text(text = stringResource(id = R.string.lastName))},
                placeholder = { Text(text = "Salem")},
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor              = Color(108, 99, 255),
                    cursorColor            = Color(108, 99, 255),
                    leadingIconColor       = Color(108, 99, 255),
                    focusedBorderColor     = Color(108, 99, 255),
                    unfocusedBorderColor   = Color(108, 99, 255),
                    focusedLabelColor      = Color(108, 99, 255),
                    unfocusedLabelColor    = Color(108, 99, 255),
                    placeholderColor       = Color(108, 99, 255),
                ),
                singleLine =  true
            )
        }

        val emailValue  = remember{mutableStateOf("")}
        OutlinedTextField(
            value         =   emailValue.value ,
            onValueChange = { emailValue.value = it },
            modifier      =
            Modifier
                .padding(start = 20.dp, end = 20.dp, top = 5.dp)
                .fillMaxWidth()
                .constrainAs(email) {
                    top.linkTo(rowName.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = { Text(text = stringResource(id = R.string.email))},
            placeholder = { Text(text = stringResource(id = R.string.email))},
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor              = Color(108, 99, 255),
                cursorColor            = Color(108, 99, 255),
                leadingIconColor       = Color(108, 99, 255),
                focusedBorderColor     = Color(108, 99, 255),
                unfocusedBorderColor   = Color(108, 99, 255),
                focusedLabelColor      = Color(108, 99, 255),
                unfocusedLabelColor    = Color(108, 99, 255),
                placeholderColor       = Color(108, 99, 255),
            ),
            singleLine = true
        )

        val passwordValue  = remember{mutableStateOf("")}
        OutlinedTextField(
            value         =   passwordValue.value ,
            onValueChange = { passwordValue.value = it },
            modifier =
            Modifier
                .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                .fillMaxWidth()
                .constrainAs(password) {
                    top.linkTo(email.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            label = { Text(text = stringResource(id = R.string.password))},
            placeholder = { Text(text = stringResource(id = R.string.password))},
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor              = Color(108, 99, 255),
                cursorColor            = Color(108, 99, 255),
                leadingIconColor       = Color(108, 99, 255),
                focusedBorderColor     = Color(108, 99, 255),
                unfocusedBorderColor   = Color(108, 99, 255),
                focusedLabelColor      = Color(108, 99, 255),
                unfocusedLabelColor    = Color(108, 99, 255),
                placeholderColor       = Color(108, 99, 255),
            ),
            singleLine = true
        )

        Button(
            onClick  = {
                val firstname     = firstName.value
                val lastname      = lastName.value
                val userEmail     = emailValue.value
                val userPassword  = passwordValue.value

                if (firstname.isEmpty())
                {
                    Toast.makeText(context, "First Name is empty", Toast.LENGTH_SHORT).show()
                }
                else if (lastname.isEmpty())
                {
                    Toast.makeText(context, "Last name is empty", Toast.LENGTH_SHORT).show()
                }
                else if (userEmail.isEmpty())
                {
                    Toast.makeText(context, "User Email is empty", Toast.LENGTH_SHORT).show()
                }
                else if (userPassword.isEmpty())
                {
                    Toast.makeText(context, "User password is empty", Toast.LENGTH_SHORT).show()
                }
                else
                {
                   FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail , userPassword).addOnCompleteListener{
                        it.addOnSuccessListener {
                            val userMap = HashMap<String , Any>()
                            userMap["name"]     = "$firstname $lastname"
                            userMap["email"]    =  userEmail
                            userMap["password"] =  userPassword
                            FirebaseFirestore.getInstance().collection("users")
                                .document(FirebaseAuth.getInstance().currentUser!!.uid)
                                .set(userMap)
                                .addOnFailureListener {
                                    Toast.makeText(context, ""+it.message, Toast.LENGTH_SHORT).show()
                                }

                            val intent = Intent(context , MainActivity::class.java)
                                context.startActivity(intent)
                                activity?.finish()

                            Toast.makeText(context , "Register Success", Toast.LENGTH_SHORT).show()
                        }
                        it.addOnFailureListener { error ->
                            Toast.makeText(context, ""+error.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                       },
            modifier =
            Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
                .constrainAs(register) {
                    top.linkTo(password.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            colors   = ButtonDefaults.textButtonColors(
                        backgroundColor = Color(108, 99, 255),
                        contentColor    = Color.White,
            )
        ) {
            Text(text = "Register")
        }
    }
}
    
    



 


