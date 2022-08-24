package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.CardItemBg
import com.cemreonur.ub10_youtube.ui.theme.DividerColor
import com.cemreonur.ub10_youtube.ui.theme.Yellow200
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.DrinksViewModel
import mhmd.salem.coffe.data.DrinksData
import mhmd.salem.coffe.ui.theme.Typography
import java.util.*

@Composable
fun DrinksScreen(navController: NavController){

    BackButton(navController = navController)

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp, start = 20.dp , bottom = 56.dp)
        .verticalScroll(rememberScrollState())
    )
    {
        val (arrowRef, textChooseRef ,imgRef , boxRef , priceRef ) = createRefs()

            Box(modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(40.dp)
                .background(Yellow500)
                .constrainAs(arrowRef)
                {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
                .clickable {
                    navController.navigate(Screen.Home.route)
                    {
                        navController.popBackStack()
                    }
                }
                ,
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Arrow back",
                    tint = Color(0xE4FFFFFF),
                    modifier = Modifier.size(24.dp)
                )
            }


        Text(
            text       = "Choose the \nfruits you love",
            fontFamily = Typography.h1.fontFamily,
            fontSize   = 25.sp,
            modifier   = Modifier
                .padding(top = 30.dp)
                .constrainAs(textChooseRef)
                {
                    top.linkTo(arrowRef.bottom)

                })

            Box(
                modifier = Modifier
                    .padding(start = 0.dp, top = 15.dp, end = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(170.dp)
                    .background(Color(215, 235, 255))
                    .constrainAs(boxRef)
                    {
                        top.linkTo(textChooseRef.bottom)
                        start.linkTo(parent.start)
                    },
                contentAlignment = Alignment.Center
            )

            {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) 
                {
                    Image(
                        painter = painterResource(id = R.drawable.fdsf),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.size(150.dp))

                    ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                        val (discount ,  icon, text) = createRefs()

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White)
                                .padding(10.dp)
                                .constrainAs(discount)
                                {
                                    top.linkTo(parent.top)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        ) {
                            Text(
                                text = "Discount Sale",
                                fontFamily = Typography.h1.fontFamily,
                                fontSize = 13.sp,
                                color =  Color(0xE2796C6C)
                            )
                        }

                        Icon(
                            painter = painterResource(id = R.drawable.sourcedfrom),
                            contentDescription = "Natural Icon",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .size(24.dp)
                                .constrainAs(icon)
                                {
                                    top.linkTo(discount.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                })

                        Text(
                            text       = "SOURCED CAREFULLY RESPONSIBLY",
                            fontFamily = Typography.h1.fontFamily,
                            fontSize   = 13.sp,
                            textAlign  = TextAlign.Center,
                            modifier   = Modifier
                                .padding(top = 10.dp)
                                .constrainAs(text)
                                {
                                    top.linkTo(icon.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                        )
                        Row(modifier = Modifier
                            .padding(10.dp)
                            .constrainAs(priceRef)
                            {
                                bottom.linkTo(parent.bottom)
                                end.linkTo(parent.end)
                            }
                        ){
                            Text(
                                text       = "$",
                                fontSize   = 8.sp,
                                color      = Color(30, 40, 67),
                                fontFamily = Typography.h1.fontFamily
                            )
                            Text(
                                text       = "10.55",
                                fontSize   = 12.sp,
                                color      = Color(30, 40, 67),
                                fontFamily = Typography.h1.fontFamily
                            )
                        }
                    }
                }
            }

        Box(modifier = Modifier
            .padding(top = 10.dp)
            .wrapContentHeight()
            .wrapContentWidth()
            .constrainAs(imgRef)
            {
                top.linkTo(boxRef.bottom)
                start.linkTo(parent.start)
            }
        )
        {
            GetAllDrinks(navController = navController)
        }
    }
}

@Composable
fun GetAllDrinks(drinksMvvm : DrinksViewModel = viewModel() , navController: NavController)
{
        drinksMvvm.getAllDrinks()
    val drinks by drinksMvvm.drinksLiveData.observeAsState(initial =  Collections.emptyList())

    LazyRow{
        items(drinks){
            DrinkRow(data = it , navController = navController)
        }
    }
}

@Composable
fun DrinkRow(data : DrinksData , navController: NavController)
{
    ConstraintLayout(modifier = Modifier.padding(10.dp)) {
        val (mainBox  , child1 ) = createRefs()
        Box(
            modifier = Modifier
                .padding(top = 25.dp)
                .clip(RoundedCornerShape(15.dp))
                .width(160.dp)
                .height(250.dp)
                .background(Color(215, 235, 255).copy(0.8f))
                .constrainAs(child1)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
        {}

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .width(220.dp)
                .height(250.dp)
                .background(Color(215, 235, 255))
                .clickable {
                    navController.navigate("DrinksDetailsScreen?" +
                            "firstName=${data.firstName}"    +
                            "&secondName=${data.secondName}" +
                            "&background=${data.background}" +
                            "&energy=${data.energy}"         +
                            "&id=${data.id}"                 +
                            "&imgUrl=${data.imgUrl}"         +
                            "&natural=${data.natural}"       +
                            "&price=${data.price}"              +
                            "&vitamin=${data.vitamin}"          +
                            "&carbohydrate=${data.carbohydrate}"+
                            "&water=${data.water}"              +
                            "&details=${data.details}"
                    )
                    {
                       // navController.popBackStack()
                    }
                }
                .constrainAs(mainBox)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
        )
        {
           ConstraintLayout(modifier = Modifier
               .padding(5.dp)
               .width(220.dp)
               .height(250.dp))
           {
               val (imageRef , PriceRef) = createRefs()

            Column(modifier    = Modifier.padding(start = 10.dp, top = 10.dp)) {
                Text(
                    text = data.firstName,
                    fontFamily  = Typography.body1.fontFamily,
                    fontSize    = 30.sp,
                    color       = Color.White
                )
                Text(
                    text = data.secondName,
                    fontFamily  = Typography.body1.fontFamily,
                    fontSize    = 35.sp,
                    color       = Color.White.copy(0.7f)
                )
            }
               GlideImage(
                  imageModel = data.imgUrl,
                   modifier  = Modifier
                       .constrainAs(imageRef)
                   {
                       top.linkTo(parent.top)
                       end.linkTo(parent.end)
                       bottom.linkTo(parent.bottom)
                   },
                   contentScale = ContentScale.Inside
               )
               
               Column(
                   modifier = Modifier.constrainAs(PriceRef)
                   {
                       start.linkTo(parent.start)
                       bottom.linkTo(parent.bottom)
                   }
               ) {
                   Text(
                       text = "Price",
                       color = Color.White,
                       fontFamily = Typography.h1.fontFamily,
                       fontSize = 15.sp,
                   )
                  Row{
                      Text(
                          text = "$",
                          color = Color(30, 40, 67),
                          fontFamily = Typography.h1.fontFamily
                      )
                      Text(
                          text = data.price.toString(),
                          fontSize = 25.sp,
                          color = Color(30, 40, 67),
                          fontFamily = Typography.h1.fontFamily
                      )
                  }
               }
           }
        }
    }
}


@Composable
fun  BackButton(navController: NavController){
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        // Handle back press
        navController.navigate(Screen.Home.route){
            navController.popBackStack()
        }
    }
}
