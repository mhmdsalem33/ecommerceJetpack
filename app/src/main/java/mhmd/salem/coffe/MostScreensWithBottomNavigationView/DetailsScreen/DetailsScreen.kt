package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.*
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ui.theme.Typography

@Composable
fun DetailsScreen(navController: NavController , name :String? , ImgUrl : String? , Id : String?){
      BackPress(navController = navController)


        val activity = (LocalContext.current as? Activity)
        ConstraintLayout(modifier = Modifier
            .padding(start = 30.dp, top = 30.dp, end = 30.dp, bottom = 56.dp)
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ) {

            val (
                headerBoxRef  ,
                mealNameRef   ,
                mealImg       ,
                descriptionRef,
                fitRef,
                addToCardRef) = createRefs()


                Row(   // Header Box
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement =  Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .constrainAs(headerBoxRef) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                )
                {
                    Box(  // Start Box Back
                        contentAlignment = Alignment.Center ,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CardItemBg)
                            .clickable {
                                navController.navigate(Screen.Home.route){ // Yerg3 3ala el home
                                    navController.popBackStack()  // yenhy el activity
                                }
                            }
                    ) {

                        Icon(
                            painter   = painterResource(id = R.drawable.arrow_left),
                            contentDescription = "Arrow Back Icon",
                            modifier = Modifier.size(24.dp),
                            tint     = IconColor
                        )
                    } // End Box Back

                    Box(  // Start Box Shopping
                        contentAlignment = Alignment.Center ,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(CardItemBg)

                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bag),
                            contentDescription = "Menu Icon",
                            modifier = Modifier.size(20.dp),
                            tint = IconColor
                        )
                    }  // End Box Shopping
                }//End Header Box

            Card(modifier = Modifier   //  Start Image Meal
                .padding(top = 20.dp)
                .size(220.dp)
                .constrainAs(mealImg) {
                    top.linkTo(headerBoxRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                shape = CircleShape) {

               GlideImage(
                   imageModel = ImgUrl,
                   modifier   = Modifier.clip(CircleShape)
               )
                
            }  //  End Image Meal

            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 20.dp, start = 20.dp)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .constrainAs(mealNameRef) {
                        top.linkTo(mealImg.bottom)
                    }
            ) {
                Column {   // Start Meal Name

                    Text(
                        text        =  name.toString(),
                        fontFamily  =  Typography.body1.fontFamily,
                        fontSize    =  20.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {  // Start Meal Price
                        Text(
                            text       = "$ ",
                            fontFamily = Typography.body1.fontFamily,
                            color      = Yellow500,
                            fontSize   =  15.sp
                        )
                        Text(
                            text       = "10.55" ,
                            fontWeight =  FontWeight.Bold,
                            fontSize   = 15.sp
                        )
                    } // End Meal Price

                }  // End Meal Name

                var quantity by rememberSaveable{ mutableStateOf(0)}
                Row (verticalAlignment = Alignment.CenterVertically){ // Start Quantity
                    Icon(
                        painter    = painterResource(id = R.drawable.minus),
                        contentDescription = "-" ,
                        modifier   = Modifier
                            .size(17.dp)
                            .clickable {
                                if (quantity == 0) {
                                    Log.d("testApp", "Sorry Quantity is zero")
                                } else {
                                    quantity--
                                }
                            }
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text( text = quantity.toString() , fontWeight = FontWeight.Bold )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(7.dp))
                            .background(Yellow500)
                            .padding(5.dp)
                            .clickable {
                                quantity++
                            }

                    ) {
                        Icon(
                            painter     = painterResource(id = R.drawable.add),
                            contentDescription = "add +",
                            modifier    = Modifier.size(24.dp),
                            tint        = Color.White
                        )
                    }
                }  // Ent Quantity

            }//  End of Container of Meal name , Price , Quantity

            Text(  // Start Description
                text = "There are Many variants of Passages of Lorem Ipsum availabe , but the majority have sufferd alteration in some form",
                modifier = Modifier
                    .padding(start = 20.dp, top = 10.dp)
                    .fillMaxWidth()
                    .constrainAs(descriptionRef)
                    {
                        top.linkTo(mealNameRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Justify,
                fontSize = 13.sp,
                fontFamily =  Typography.h1.fontFamily,
                color = Color(0xFFA3A3A3)
            )

            Box(  // Start Of Fits
                modifier =
                Modifier
                    .padding(start = 20.dp, top = 30.dp, end = 10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .height(50.dp)
                    .background(Color(0xFFF7F7F7))
                    .constrainAs(fitRef) {
                        top.linkTo(descriptionRef.bottom)
                    },
                    contentAlignment = Alignment.Center
                    )
            {

                         FitDetails()

            } // End Of Fits

            Button( 
                onClick  = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 30.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .constrainAs(addToCardRef) {
                        top.linkTo(fitRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Yellow500,
                    contentColor    = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bag),
                    contentDescription = "shop"  ,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier  = Modifier.width(10.dp))
                Text(text        = "Add to card")
            }
        }
}

@Composable
fun FitDetails(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.calori), contentDescription = "Calori")
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "540 Kcal")
        }

        Row(modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Color.Black)){}

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.star), contentDescription = "Rating")
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = "5.0")
        }

        Row(modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Color.Black)){}

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter     = painterResource(id = R.drawable.schedule), contentDescription = "Time")
            Spacer(modifier   = Modifier.width(6.dp))
            Text(text = "20 min") }
    }
}
@Composable
fun BackPress(navController: NavController){
    var backHandlingEnabled by remember { mutableStateOf(true) }
    BackHandler(backHandlingEnabled) {
        // Handle back press
        navController.navigate(Screen.Home.route){
            navController.popBackStack()
        }
    }
}
