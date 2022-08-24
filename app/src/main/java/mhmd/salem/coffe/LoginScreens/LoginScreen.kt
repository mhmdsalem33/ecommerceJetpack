package mhmd.salem.coffe.LoginScreens


import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.purplish
import com.google.firebase.auth.FirebaseAuth
import mhmd.salem.coffe.Activites.MainActivity
import mhmd.salem.coffe.R

@Composable
fun Login(
    navController: NavController
){

    val auth = FirebaseAuth.getInstance()
    val activity = (LocalContext.current as? Activity)

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(purplish)
        .verticalScroll(rememberScrollState())
    ) {
        val (registerRef , MainRef) = createRefs()

        Surface(
            color = Color.White, modifier = Modifier
                // .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(550.dp)
                .constrainAs(MainRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            shape = RoundedCornerShape(bottomEnd = 60.dp , bottomStart = 60.dp))
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter             = painterResource(id = R.drawable.cofee),
                    contentDescription  = "",
                    contentScale        = ContentScale.Crop,
                    modifier =  Modifier.padding(top = 20.dp, bottom = 20.dp).fillMaxWidth().height(300.dp)
                )
                val emailValue = remember{ mutableStateOf("")}
                OutlinedTextField(
                    modifier      = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    value         =   emailValue.value,
                    onValueChange = { emailValue.value = it } ,
                    label         = { Text(text = stringResource(id = R.string.email))},
                    placeholder   = { Text(text = "mohamed@yahoo.com")},
                    leadingIcon   = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
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
                    singleLine = true,
                )

                val passwordValue        = remember{ mutableStateOf("")}
                var passwordVisibility by remember{ mutableStateOf(false)}

                val icon = if (passwordVisibility)
                    painterResource(id = R.drawable.ic_baseline_visibility_24)
                else
                    painterResource(id = R.drawable.ic_baseline_visibility_off_24)

                Spacer(modifier = Modifier.size(2.dp))
                OutlinedTextField(
                    value          =   passwordValue.value,
                    onValueChange  = { passwordValue.value = it } ,
                    label          =  { Text(text = stringResource(id = R.string.password))},
                    placeholder    =  { Text(text = "Enter your password")},
                    leadingIcon    =  { IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "")
                    }},

                    trailingIcon   = {
                        IconButton(onClick = {
                            passwordVisibility = !passwordVisibility
                        }) {
                            Icon(
                                painter = icon,
                                contentDescription = "Visible Icon"
                            )
                        }
                    },
                    modifier = Modifier
                        .paddingFromBaseline(top = 10.dp)
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
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
                    singleLine = true,
                    keyboardOptions   = KeyboardOptions(
                        keyboardType  = KeyboardType.Password
                    ),
                    visualTransformation =  if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.size(20.dp))
                val context = LocalContext.current
                Button(
                    onClick  = {
                        val userEmail = emailValue.value
                        val userPassword = passwordValue.value

                        if (userEmail.isEmpty())
                        {
                            Toast.makeText(context, "user name is empty", Toast.LENGTH_SHORT).show()
                        }
                        else if (userPassword.isEmpty())
                        {
                            Toast.makeText(context, "user password is empty", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            auth.signInWithEmailAndPassword(userEmail , userPassword).addOnCompleteListener{
                                it.addOnSuccessListener {
                                    Toast.makeText(context, "LogIn Success", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(context , MainActivity::class.java)
                                    context.startActivity(intent)
                                    activity?.finish()
                                }
                            }
                        }
                    },
                    shape    = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 40.dp, end = 40.dp),
                    colors              = ButtonDefaults.buttonColors(
                        backgroundColor = Color(108, 99, 255),
                        contentColor    = Color.White
                    )
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(50.dp , 40.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(registerRef) {
                    top.linkTo(MainRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            Text(
                text  = stringResource(id = R.string.account),
                color =  Color(228,228,228)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Button(
                onClick = {
                    navController.navigate("register")
                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(228,228,228),
                    contentColor =  Color(108, 99, 255)
                )
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }
    }
}