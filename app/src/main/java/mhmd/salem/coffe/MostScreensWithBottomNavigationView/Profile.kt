package mhmd.salem.coffe.MostScreensWithBottomNavigationView


import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowUp

import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.Yellow200
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope

import kotlinx.coroutines.launch
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.PersonalInformationViewModel
import mhmd.salem.coffe.ui.theme.Typography
import java.util.function.IntConsumer


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun Profile(userMvvm : PersonalInformationViewModel = viewModel() , navController: NavController)
{
        userMvvm.getUserInformation()
    var user = userMvvm.userLiveData.observeAsState(initial =  emptyList())


     val imageUri = remember { mutableStateOf<Uri?>(null) }
     val selectedImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
          imageUri.value = uri
      }
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {

        val (arrowBackRef ,imageRef , boxTransparent,  nameRef , contentBottomRef ) = createRefs()

            //todo Show dialog
        val showDialog = remember{ mutableStateOf(false)}
        if (showDialog.value)
        {
            AlertDialog(
                title   = { Text(text = "Profile Image" , fontFamily =  Typography.h1.fontFamily , fontSize = 18.sp)},
                text    = { Text(text = "Are you want to change profile image?" , fontFamily =  Typography.h1.fontFamily )},
                onDismissRequest = { showDialog.value = false } ,
                confirmButton =
                {
                    Button(
                        onClick = {
                            userMvvm.imageUri = imageUri.value

                                if (imageUri.value != null)
                                {
                                    GlobalScope.launch (Dispatchers.IO) {
                                    userMvvm.uploadProfileImage(context = context)
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context, "You are not selected any image", Toast.LENGTH_SHORT).show()
                                }

                            showDialog.value = false
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Yellow200
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    ) {
                        Text(text = "Change" )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {showDialog.value = false }
                        ,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Yellow200
                        ),
                        modifier = Modifier.clip(RoundedCornerShape(15.dp))
                    )
                    {
                        Text(text = "Cancel")
                    }
                }
            )
        }

        for (i in user.value)
        GlideImage(  // todo Profile Image
            imageModel = if (i.profileImg.isNotEmpty()) i.profileImg else R.drawable.profile2,
            modifier  = Modifier
                .fillMaxWidth()
                .height(450.dp)
                .clickable {
                    selectedImage.launch("image/*")
                    userMvvm.imageUri = imageUri.value
                    showDialog.value = true

                }
                .constrainAs(imageRef)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentScale = ContentScale.Crop
        )


            Box( // todo Arrow Back Box
                modifier = Modifier
                    .padding(start = 20.dp, top = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .background(Color(0xFFF2F2F2))
                    .clickable {
                        navController.navigate(Screen.Home.route)
                        {
                            navController.popBackStack()
                        }
                    }
                    .constrainAs(arrowBackRef)
                    {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "arrow Left" ,
                    modifier = Modifier.size(24.dp)
                )
            }


            Box(modifier = Modifier // todo box white inside image
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White)
                .constrainAs(boxTransparent)
                {
                    bottom.linkTo(imageRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
            {}

            Surface(  //
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(start = 20.dp)
                    .constrainAs(contentBottomRef)
                    {
                        top.linkTo(imageRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                color = Color.White

            )
            {

                ConstraintLayout(modifier = Modifier.fillMaxSize())
                {

                val (name , fakeImageRef) = createRefs()

                    Column(modifier = Modifier
                        .constrainAs(name)
                        {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }) {
                        //todo user name
                        for (i in user.value)
                            Text(
                                text         =  i.name ,
                                fontFamily   =  Typography.h1.fontFamily ,
                                fontSize     =  18.sp)

                        Text(
                            text       = "Norway" ,
                            fontFamily = Typography.h1.fontFamily ,
                            fontSize   = 12.sp ,
                            color      = Color(0xBE414141))
                    }

                    Box(// todo Box images
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .constrainAs(imageRef)
                            {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                    {

                        Row(  // todo Fake images Row
                            modifier              = Modifier
                                .padding(top = 50.dp, end = 20.dp)
                                .fillMaxWidth()    ,
                            verticalAlignment     = Alignment.CenterVertically ,
                            horizontalArrangement = Arrangement.SpaceBetween)
                        {
                            FakeImages(R.drawable.primavera_pizza)
                            FakeImages(R.drawable.salad_pesto_pizza)
                            FakeImages(R.drawable.primavera_pizza)
                        }
                    }


                    Box() {  // todo  Bottom Sheet
                        BottomSheet()
                    }

                }

            }
    }

}

@Composable
fun FakeImages(imgId : Int){
    Box(
        modifier = Modifier
            .size(70.dp)
    ) {
        Image(painter = painterResource(id = imgId), contentDescription = "")
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun BottomSheet(){
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState     =   bottomSheetState,
        sheetContent   = { MyContentSheet() },
        sheetShape     =   RoundedCornerShape(10.dp),
        sheetElevation =   0.dp,
        modifier = Modifier
            .padding(0.dp),
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Transparent,

        ) {
        MainContent(scope = scope , bottomSheetState =  bottomSheetState)
    }
}

@ExperimentalMaterialApi
@Composable
fun MainContent(scope: CoroutineScope , bottomSheetState: ModalBottomSheetState) {  //todo Icon  it will show content

    Box(contentAlignment = Alignment.TopEnd , modifier = Modifier
        .padding(end = 10.dp)
        .fillMaxWidth()) {
        Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Edited Name",
            modifier = Modifier.clickable {
                scope.launch {
                    bottomSheetState.show() }
            })
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MyContentSheet(userMvvm: PersonalInformationViewModel = viewModel()) { // todo Content Will show to user


    val name  = remember{ mutableStateOf("")}
    val context            = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager       = LocalFocusManager.current

    Column (modifier = Modifier){
        Box(
            modifier = Modifier
                //  .padding(bottom = 56.dp)
                .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp))
                .fillMaxWidth()
                .height(200.dp) // Bottom SHeet Height
                .background(Color.White),
            contentAlignment = Alignment.Center
        ){
            TextField(
                value         =   name.value,
                onValueChange = { name.value = it },
                label         = { Text(text = "Enter new name")},
                modifier      = Modifier
                    .padding(0.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                ,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor         = Color(0xFFF2F2F2),
                    focusedIndicatorColor   = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor       = Color.Black,
                    unfocusedLabelColor     = Color.Black,
                    textColor               = Color.Black,
                    cursorColor             = Color.Black
                ),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription ="" )
                    }
                }
                ,
                keyboardOptions = KeyboardOptions(
                    imeAction   = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (name.value.isEmpty())
                        {
                            Toast.makeText(context, "User name is empty", Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            userMvvm.userName  = name.value
                            userMvvm.updateUserInformation(context = context)
                            keyboardController?.hide()
                            focusManager.clearFocus()
                            name.value = ""
                        }
                    }
                )
            )
        }
    }
}


