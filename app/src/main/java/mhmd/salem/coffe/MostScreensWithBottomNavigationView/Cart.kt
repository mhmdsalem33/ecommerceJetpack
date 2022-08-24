package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.*
import mhmd.salem.coffe.Activites.OrderActivity
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.CartViewModel
import mhmd.salem.coffe.data.CartData
import mhmd.salem.coffe.ui.theme.Typography
import java.io.Serializable
import kotlin.math.roundToInt


@ExperimentalMaterialApi
@Composable
fun Cart(navController: NavController , cartMvvm: CartViewModel = viewModel())
{
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 56.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {
        val (backRef , cartRef , priceRef ) = createRefs()
        Row(    // Start  todo Header Row
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier              = Modifier
                .fillMaxWidth()
                .constrainAs(backRef)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        {
            Box(   // todo Arrow back Box
                contentAlignment  = Alignment.Center,
                modifier          = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .clickable {
                        navController.navigate(Screen.Home.route)
                        {
                            navController.popBackStack()
                        }
                    }
                    .background(Color(0xFFF2F2F2)))
            {
                Icon( //todo Arrow back Icon
                    painter             = painterResource(id = R.drawable.arrow_left ),
                    contentDescription  = "arrowLEft",
                    modifier            = Modifier.size(24.dp)
                )
            }
            Box(  //todo Delete All items in  Cart
                contentAlignment = Alignment.Center,
                modifier         = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .clickable {
                        cartMvvm.deleteAllInformationFormCart(context = context)
                    }
                    .background(Color(0xFFF2F2F2))
            )
            {
                Icon(
                    painter            = painterResource(id = R.drawable.deleted),
                    contentDescription = "Delete All",
                    modifier           = Modifier.size(18.dp),
                    tint               = Color(0xFF000000)
                )
            }
        }  //  End todo Header Row
        Box(
            modifier =
            Modifier
                .padding(top = 30.dp)
                .fillMaxWidth()
                .constrainAs(cartRef)
                {
                    top.linkTo(backRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        {
            GetCartInformation()
        }
        Box(modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .constrainAs(priceRef)
            {
                top.linkTo(cartRef.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
        {
            PriceBox(navController = navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun GetCartInformation(cartMvvm : CartViewModel = viewModel())
{

     cartMvvm.getCartInformation()
    val data     by cartMvvm.cartStateFlow.collectAsState(initial = emptyList())
    val cartData by cartMvvm.cartLiveData.observeAsState(initial = emptyList())

    LazyColumn(Modifier.height(400.dp)){
       items(
          items  =    data,
           key   =  { data ->
                data.id
           }
       ){ data ->
           CartRow(data)
       }
    }
}

@ExperimentalMaterialApi
@Composable
fun CartRow(data: CartData, cartMvvm: CartViewModel = viewModel())
{
    val context = LocalContext.current
    Box(modifier = Modifier
        .padding(bottom = 10.dp)
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
                  imageModel   = data.productImg,
                  modifier     = Modifier.size(70.dp),
                  contentScale = ContentScale.Inside
              )
            }

            var totalPrice = "${data.totalPrice}"
           // var totalPrice by remember { mutableStateOf("${data.totalPrice}") }

            Column(Modifier.weight(1f)) {

                Text(
                    text       = data.productName,
                    fontFamily = Typography.h1.fontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 15   .sp,
                    color      = Color(0xFF35314A)

                )

                Text(
                    text = data.Description,
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 11.sp,
                    color    = Color(0xFF6C697A),
                    maxLines = 1
                )
                //val cartFinalPrice =  (totalPrice.toInt().times(100.0)).roundToInt() / 100.0
               Row() {
                   Text(text = "$",fontSize = 10.sp)
                   Text(
                       text = totalPrice,
                       fontWeight = FontWeight.Bold,
                       fontSize = 12.sp,
                       color = Color(0xFF211E2C)
                   )
               }
            }

               val context  = LocalContext.current
               var quantity = remember{ mutableStateOf(data.totalQuantity.toInt())}
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
                     cartMvvm.id  = data.id
                     cartMvvm.deleteCurrentItemFromCart(context = context)
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
                                    if (quantity.value == 1) {
                                        Log.d("testApp", "Sorry Quantity is ${quantity.value}")
                                    } else {
                                        quantity.value--
                                    }
                                    val price = data.productPrice * quantity.value
                                    val productPrice = (price.times(100.0)).roundToInt() / 100.0
                                    totalPrice = productPrice.toString()

                                    cartMvvm.productId = data.id
                                    cartMvvm.totalPrice = totalPrice.toDouble()
                                    cartMvvm.totalQuantity = quantity.value.toString()
                                    cartMvvm.updateNewPrice(context = context)
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
                            text       = quantity.value.toString() ,
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
                                    quantity.value++
                                    val price = data.productPrice * quantity.value
                                    val productPrice = (price.times(100.0)).roundToInt() / 100.0
                                    totalPrice = productPrice.toString()
                                    cartMvvm.productId = data.id
                                    cartMvvm.totalPrice = totalPrice.toDouble()
                                    cartMvvm.totalQuantity = quantity.value.toString()
                                    cartMvvm.updateNewPrice(context = context)
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

@Composable
fun PriceBox(cartMvvm: CartViewModel = viewModel() , navController: NavController)
{
        // todo Prepare Cart price
    var totalPrice = 0.0
    for ( i in cartMvvm.cartMutableStateOf)
    {
        totalPrice += i.totalPrice
    }
    val totalAmount = (totalPrice.times(100.0)).roundToInt() / 100.0  // todo Convert Price to 0.00


    Column(modifier  = Modifier.fillMaxWidth()) {   // todo Box Of Bottom Information

        Row(  // todo SELECTED ITEMS
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text       = "Selected items",
                color      = Color(0xFF8C8A97) ,
                fontFamily = Typography.h1.fontFamily ,
                fontSize   = 13.sp)
            Row() {
                Text(
                    text     = "$ " ,
                    color    = Color(0xFF3E3A51),
                    fontSize = 10.sp)
                Text(
                    text        = totalAmount.toString(),
                    color       = Color(0xFF3E3A51),
                    fontFamily  = Typography.h1.fontFamily,
                    fontWeight  = FontWeight.Bold ,
                    fontSize    = 15.sp)
            }
        }

        // todo Shipping Fee
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {

            Text(
                text       = "Shipping fee" ,
                color      = Color(0xFF8C8A97),
                fontFamily = Typography.h1.fontFamily ,
                fontSize   = 13.sp)
            Row() {
                Text(
                    text     = "$ ",
                    color    = Color(0xFF3E3A51),
                    fontSize = 10.sp)
                Text(
                    text       =  "10",
                    color      =  Color(0xFF3E3A51),
                    fontFamily =  Typography.h1.fontFamily,
                    fontWeight =  FontWeight.Bold ,
                    fontSize   =   15.sp)
            }
        }

           //todo VAT (Tax)
        val taxFee     =  totalPrice * 14/100
        val taxConvert =  (taxFee.times(100.0)).roundToInt() / 100.0
     //   val taxFee = totalPrice * 0.10
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text         = "Vat",
                color        = Color(0xFF8C8A97),
                fontFamily   = Typography.h1.fontFamily ,
                fontSize     = 15.sp)
            Row() {
                Text(
                    text        = "$ " ,
                    color       = Color(0xFF3E3A51) ,
                    fontSize    = 10.sp)
                Text(
                    text        = "$taxConvert" ,
                    color       = Color(0xFF2D2942) ,
                    fontFamily  = Typography.h1.fontFamily,
                    fontWeight  = FontWeight.Bold ,
                    fontSize    = 15.sp)
                Text(
                    text        = "  14%" ,
                    color       = Color(0xFF3E3A51) ,
                    fontFamily  = Typography.h1.fontFamily ,
                    fontSize    = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.Black)) {
            
        }
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(
                text       = "Subtotal " ,
                color      = Color(0xFF2D2942),
                fontWeight = FontWeight.Bold ,
                fontFamily = Typography.h1.fontFamily ,
                fontSize   = 15.sp)
            Row() {  //todo SUB TOTAL

                val subTotal        =  (totalAmount + 10 + taxConvert)
                val subTotalConvert =  (subTotal.times(100.0)).roundToInt() / 100.0

                Text(
                    text = "$ "  ,
                    fontSize   = 10.sp)
                Text(
                    text       = subTotalConvert.toString() ,
                    color      = Color(0xFF2D2942),
                    fontFamily = Typography.h1.fontFamily,
                    fontWeight = FontWeight.Bold ,
                    fontSize   = 15.sp)
            }
        }

        val activity = (LocalContext.current as? Activity)
        val context  = LocalContext.current
        Box(modifier = Modifier
            .padding(bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .fillMaxWidth()
            .height(40.dp),
            contentAlignment = Alignment.Center
          )
        {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(40.dp)
                .width(60.dp)
                .clickable {
                    //todo send all cart data by Serializable
                    /*
                    navController.currentBackStackEntry?.arguments?.putSerializable(
                        "itemsCart", cartMvvm.cartMutableStateOf as Serializable)
                    navController.navigate(Screen.Home.route)
                    {
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            popUpTo(Screen.Home.route)
                            {
                                inclusive  = true
                            }
                        }
                    }
                     */

                    val intent = Intent(context , OrderActivity::class.java)
                        intent.putExtra("cartList"  , cartMvvm.cartMutableStateOf as Serializable)
                        context.startActivity(intent)
                   // cartMvvm.deleteAllInformationFormCart(context = context)
                }
                .background(Color(0xFFFA5033)),
                 contentAlignment = Alignment.Center)
            {
                Icon(
                    painter = painterResource(id = R.drawable.bag),
                    contentDescription = "Complete order" ,
                  //  modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}

