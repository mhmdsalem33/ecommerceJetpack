package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mhmd.salem.coffe.Activites.MainActivity
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.DrinksDetailsViewModel
import mhmd.salem.coffe.data.DrinksData
import mhmd.salem.coffe.ui.theme.Typography
import kotlin.math.roundToInt



private lateinit var drinksDetailsMvvm : DrinksDetailsViewModel
@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DrinksDetailsScreen(
        firstName    :String?,
        secondName   :String?,
        background   :String?,
        energy       :String?,
        id           :String?,
        imgUrl       :String?,
        natural      :String?,
        price        :String?,
        vitamin      :String?,
        carbohydrate :String?,
        water        :String?,
        description  :String?,
        navController: NavController,
        //drinksDetailsMvvm : DrinksDetailsViewModel = viewModel(),

)
{

         val context = LocalContext.current

    drinksDetailsMvvm = (context as MainActivity).drinksDetailsMvvm
    drinksDetailsMvvm.firstName    = firstName
    drinksDetailsMvvm.secondName   = secondName
    drinksDetailsMvvm.description  = description
    drinksDetailsMvvm.imgUrl       = imgUrl
    drinksDetailsMvvm.price        = price

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp, bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    )
    {
        val (arrowBack , img , quantityBox , nameRatingRef , descriptionRef , fitsRef , addRef) = createRefs()

        Row(
            modifier = Modifier
                .padding(end = 20.dp)
                .fillMaxWidth()
                .constrainAs(arrowBack)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp, start = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .size(40.dp)
                    .background(Yellow500)
                    .clickable {
                        navController.navigate("Drinks") {
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

            Box(  // todo  Save  Favorite Drinks
                modifier = Modifier
                    .padding(top = 10.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        val data = arrayListOf<DrinksData>(
                            DrinksData(
                                firstName!!,
                                secondName!!,
                                background!!,
                                energy!!,
                                id!!,
                                imgUrl!!,
                                natural!!,
                                price!!,
                                vitamin!!,
                                carbohydrate!!,
                                water!!,
                                description!!
                            )
                        )
                        data.let {
                            drinksDetailsMvvm.upsertDrinks(it)
                            Toast.makeText(context, "Product Saved", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .border(
                        border = BorderStroke(1.dp, Color(0xFFE8E8E8)),
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(24.dp),
                    tint     =  Yellow500
                )
            }
        }

        var totalPrice by rememberSaveable { mutableStateOf("$price") }
        
        Row(
            verticalAlignment     = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 40.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .constrainAs(img)
                {
                    top.linkTo(arrowBack.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        ) {
            Column() {
                Text(
                    text       = "Carbohydrate",
                    color      = Color(0xFFB0B2BA),
                    fontFamily = Typography.h1.fontFamily,
                    fontSize   = 15.sp
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween , verticalAlignment = Alignment.Bottom) {
                    Text(
                        text       = carbohydrate.toString()+"%" ,
                        fontFamily = Typography.h1.fontFamily ,
                        fontSize   = 25.sp ,
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.width(5.dp))

                   Text(text = "20g" , fontFamily = Typography.h1.fontFamily , fontSize = 12.sp , modifier = Modifier.padding(bottom = 10.dp))
                }

                Text(
                    text = "Water",
                    color = Color(0xFFB0B2BA),
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 15.sp
                )

                Text(
                    text       = water.toString() +"%" ,
                    fontFamily = Typography.h1.fontFamily ,
                    fontSize   = 25.sp ,
                    fontWeight = FontWeight.Bold)

                Text(
                    text =  "Price",
                    color = Color(0xFFB0B2BA),
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 15.sp
                )

                Row() {

                    Text(
                        text       = "$" ,
                        fontFamily = Typography.h1.fontFamily ,
                        fontSize   = 10.sp ,
                        fontWeight = FontWeight.Bold)

                    Text(
                        text       = totalPrice ,
                        fontFamily = Typography.h1.fontFamily ,
                        fontSize   = 20.sp ,
                        fontWeight = FontWeight.Bold)
                    
                    Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text        = "EGP" ,
                            fontFamily  = Typography.h1.fontFamily ,
                            fontSize    = 10.sp ,
                            fontWeight  = FontWeight.Bold,
                            modifier    = Modifier.padding(top = 10.dp)
                        )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

                GlideImage(
                    imageModel = background,
                    modifier   = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(200.dp)
                )
             // Img Product
        }
            var quantity by rememberSaveable{ mutableStateOf(1)}
            drinksDetailsMvvm.quantity = quantity.toString()
        Box(
            modifier = Modifier
                .padding(top = 30.dp, start = 20.dp)
                .clip(RoundedCornerShape(15.dp))
                .height(40.dp)
                .width(110.dp)
                .background(Color(0xFFFA5033).copy(0.8f))
                .padding(10.dp)
                .constrainAs(quantityBox)
                {
                    top.linkTo(img.bottom)
                    start.linkTo(parent.start)
                })
        {
            Row(
                verticalAlignment     = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.Center,
                modifier              = Modifier.fillMaxWidth()
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Minus",
                    tint = Color.White,
                    modifier = Modifier
                        .size(15.dp)
                        .clickable
                        {
                            if (quantity == 1) {
                                Log.d("testApp", "Quantity = $quantity")
                            } else {
                                quantity--
                                val serverPrice = price!!.toDouble()  // SERVER PRICE
                                val price = (quantity * serverPrice)
                                val productPrice =
                                    (price.times(100.0)).roundToInt() / 100.0  // CONVERT THIS PRIVE TO 00.03
                                totalPrice = productPrice.toString()
                            }
                        })

                Spacer(modifier = Modifier.width(15.dp))

                Text(
                    text     = quantity.toString(),
                    color    = Color.White,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.width(15.dp))

                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Minus",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            quantity++

                            val serverPrice = price!!.toDouble()  // SERVER PRICE
                            val price = (quantity * serverPrice)
                            val productPrice =
                                (price.times(100.0)).roundToInt() / 100.0  // CONVERT THIS PRIVE TO 00.03
                            totalPrice = productPrice.toString()

                        }
                )
            }
        }

        Row(   // Start Name and rating
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier              = Modifier
                .padding(top = 30.dp, start = 20.dp, end = 10.dp)
                .fillMaxWidth()
                .constrainAs(nameRatingRef)
                {
                    top.linkTo(quantityBox.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {

            Row(verticalAlignment = Alignment.CenterVertically) 
            {
                Text(
                    text = firstName.toString(),
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text       = secondName.toString(),
                    fontFamily = Typography.h1.fontFamily,
                    fontSize   = 20.sp)
            }

            Icon(
                painter = painterResource(id = R.drawable.ratingbar),
                contentDescription = "Rating",
                tint = Color(0xFFFFBA00)
            )

        } // End Name and rating


        Text(  // Start Description
            text       = description.toString(),
            textAlign  = TextAlign.Justify,
            modifier   = Modifier
                .padding(top = 10.dp, start = 20.dp, end = 10.dp)
                .constrainAs(descriptionRef)
                {
                    top.linkTo(nameRatingRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            fontFamily = Typography.h1.fontFamily,
            fontSize   = 12.sp,
            color = Color(0xFFB0B2BA)
        )// End Description

        Row(  // Start details
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(fitsRef)
                {
                    top.linkTo(descriptionRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceEvenly,

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.energy),
                    contentDescription = "energy",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "${energy.toString()}",
                    textAlign = TextAlign.Center,
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 10.sp
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.sourcedfrom),
                    contentDescription = "",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$natural",
                    textAlign = TextAlign.Center,
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 10.sp
                )
            }
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id = R.drawable.c),
                    contentDescription = "energy",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$vitamin" ,
                    textAlign = TextAlign.Center,
                    fontFamily = Typography.h1.fontFamily,
                    fontSize = 10.sp
                )
            }
        }  // End details

         val context = LocalContext.current
        Button(
            onClick = {
               drinksDetailsMvvm.sendCartDetailsToFirestore(navController = navController  , context =  context)
            },
            modifier = Modifier
                .padding(top = 30.dp, bottom = 20.dp)
                .clip(RoundedCornerShape(25.dp))
                .constrainAs(addRef)
                {
                    start.linkTo(parent.start)
                    top.linkTo(fitsRef.bottom)
                    end.linkTo(parent.end)

                },
            colors =  ButtonDefaults.buttonColors(
                      backgroundColor = Color(0xE4FA5033)
            ))
        {
            Text(
                text       =  "PROCEED TO BUY",
                color      =  Color.White,
                fontSize   =  15.sp,
                fontWeight =  FontWeight.Bold
            )
        }
    }
}

