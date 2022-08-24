package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.Activites.MainActivity
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.DrinksDetailsViewModel
import mhmd.salem.coffe.data.DrinksData
import mhmd.salem.coffe.ui.theme.Typography
import kotlin.math.roundToInt

private lateinit var drinksDetailsMvvm : DrinksDetailsViewModel

@ExperimentalMaterialApi
@Composable
fun Favorite(navController :NavController){

    val context = LocalContext.current
    // todo Define Mvvm
    drinksDetailsMvvm = ( context as MainActivity).drinksDetailsMvvm

    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    )
    {

        val (arrowRef , cartFavRef) = createRefs()

     Row(
         verticalAlignment     = Alignment.CenterVertically,
         horizontalArrangement = Arrangement.SpaceBetween,
         modifier              = Modifier
             .fillMaxWidth()
             .constrainAs(arrowRef)
             {
                 top.linkTo(parent.top)
                 start.linkTo(parent.start)
             }
     )
     {
         Box(
             modifier = Modifier
                 .padding(top = 20.dp, start = 20.dp)
                 .clip(RoundedCornerShape(10.dp))
                 .size(40.dp)
                 .background(Yellow500)

                 .clickable {

                     navController.navigate(Screen.Home.route) {
                         navController.popBackStack()
                     }

                 }
             ,
             contentAlignment = Alignment.Center
         )
         {
             Icon(
                 painter = painterResource(id = R.drawable.arrow_left),
                 contentDescription = "Arrow Back",
                 tint = Color.White,
                 modifier = Modifier.size(24.dp)
             )
         }

            Box(
                contentAlignment = Alignment.Center ,
                modifier = Modifier
                    .height(40.dp)
                    .padding(top = 20.dp)
            )
            {
                Text(
                    text = "Favorite Products",
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 18.sp
                )
            }

         Box(
             modifier = Modifier
                 .padding(top = 20.dp, end = 20.dp)
                 .clip(RoundedCornerShape(10.dp))
                 .size(40.dp)
                 .background(Yellow500)
                 .clickable {
                     drinksDetailsMvvm.clear()
                     Toast.makeText(context, "All Favorites deleted", Toast.LENGTH_SHORT).show()
                 }
             ,
             contentAlignment = Alignment.Center
         )
         {
             Icon(
                 painter = painterResource(id = R.drawable.delete),
                 contentDescription = "Delete All Favorite Items",
                 tint = Color.White,
                 modifier = Modifier.size(18.dp)
             )
         }
     }

        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .constrainAs(cartFavRef)
                {
                    top.linkTo(arrowRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {
            GetSavedProducts()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun GetSavedProducts()
{
    val context = LocalContext.current
    // todo Define Mvvm
    drinksDetailsMvvm = ( context as MainActivity).drinksDetailsMvvm

    //todo collect  saved data  from room
    val savedProducts by drinksDetailsMvvm.getAllSavedDrinks.collectAsState(initial = emptyList())

    LazyColumn{
        itemsIndexed(
            items = savedProducts ,
            key   = { _,listItem -> listItem.hashCode() }
        ){index , item ->
            val state =  rememberDismissState(    //todo remember if item delete
                    confirmStateChange = {
                            if (it == DismissValue.DismissedToStart)
                            {
                                drinksDetailsMvvm.deleteDrinks(item)
                            }
                    true
                    }
            )
            SwipeToDismiss(state = state, background = {
                val color  = when(state.dismissDirection){
                        DismissDirection.StartToEnd -> Color.Transparent
                        DismissDirection.EndToStart -> Color.Transparent
                        null -> Color.Transparent
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = color)
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 10.dp))
                {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete" ,
                        tint     = Yellow500  ,
                        modifier =
                        Modifier.align(Alignment.CenterEnd))
                }
            } ,
                dismissContent = {
                    CartFavRow(item)
                },

                directions = setOf(DismissDirection.EndToStart))
             //   Divider()  // الخط الفاصل تحت

           // CartFavRow(item)
        }
    }
}

var totalPrice = ""
@Composable
fun CartFavRow (data : DrinksData)
{
        var checkPrice by rememberSaveable { mutableStateOf(true) }
        if (checkPrice)
        {
            totalPrice = data.price
        }

    val context = LocalContext.current
    Box(modifier = Modifier
        .padding(start = 20.dp, bottom = 10.dp, end = 20.dp)
        .clip(RoundedCornerShape(12.dp))
        .fillMaxWidth()
        .height(120.dp)
        .background(Color(0xFFF3F3F3))
        .padding(20.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center)
            {
                GlideImage(
                    imageModel   = data.imgUrl,
                    modifier     = Modifier.size(70.dp),
                    contentScale = ContentScale.Inside
                )
            }

            Column(Modifier.weight(1f)) {

                Text(
                    text       = data.firstName + " " + data.secondName,
                    fontFamily = Typography.h1.fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 15   .sp,
                    color      = Color(0xFF35314A)

                )

                Text(
                    text = data.details,
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 11.sp,
                    color    = Color(0xFF6C697A),
                    maxLines = 1
                )

                Row() {
                    Text(text = "$",fontSize = 10.sp)
                    Text(
                        text =  totalPrice,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color(0xFF211E2C)
                    )
                }
            }

            var quantity  by rememberSaveable { mutableStateOf(1)}

            Column(
                Modifier
                    .weight(1f)
                    .height(120.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Top,
            ) {

                Icon(  // todo delete current item
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "",
                    modifier =  Modifier.clickable {
                        data.let {
                            drinksDetailsMvvm.deleteDrinks(it)
                        }
                    }
                )

                Column(  //todo Cart -- and ++
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally ,
                    verticalArrangement = Arrangement.Bottom)
                {
                    Row(verticalAlignment = Alignment.CenterVertically)
                    {
                        Box(  // todo --
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(25.dp)
                                .background(Color(0xFFF2F2F2))
                                .border(2.dp, Color(0xFF444056), RoundedCornerShape(10.dp))
                                .clickable
                                {
                                    if (quantity == 1) {
                                        Log.d("testApp", "Sorry Quantity is ${quantity}")
                                    } else {
                                        checkPrice = false
                                        quantity--
                                        val price = data.price.toDouble() * quantity
                                        val productPrice = (price.times(100.0)).roundToInt() / 100.0
                                        totalPrice = productPrice.toString()
                                    }
                                }
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.minus ),
                                contentDescription = "arrowLEft",
                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text       = quantity.toString() ,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 15.sp,
                            color      = Color(0xFF211E2C)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Box(   // todo ++
                            contentAlignment = Alignment.Center,
                            modifier         = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .size(25.dp)
                                .background(Color(0xFFF2F2F2))
                                .border(2.dp, Color(0xFF444056), RoundedCornerShape(10.dp))
                                .clickable
                                {
                                    checkPrice = false
                                    quantity++
                                    val price = data.price.toDouble() * quantity
                                    val productPrice = (price.times(100.0)).roundToInt() / 100.0
                                    totalPrice = productPrice.toString()

                                }
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.add ),
                                contentDescription = "arrowLEft",
                                modifier = Modifier
                                    .size(18.dp)
                            ) }  // End todo ++
                    }
                }
            }
        }
    }
}

