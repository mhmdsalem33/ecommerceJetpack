package mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.cemreonur.ub10_youtube.ui.theme.Yellow200
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.BackButton
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.RestaurantsViewModel
import mhmd.salem.coffe.data.RestaurantsData
import mhmd.salem.coffe.ui.theme.Typography
import java.util.*

@Composable
fun PizzaScreen(navController: NavController){

    BackButton(navController = navController)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xD3F3F3F3)))
    {

        val (RestaurantsNameRef , Restaurants) = createRefs()
        Row(
            modifier = Modifier
                .padding(start = 30.dp, top = 25.dp, end = 10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(RestaurantsNameRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {
            RestaurantCount()
        }

       ConstraintLayout(modifier = Modifier
           .padding(start = 25.dp, top = 20.dp, end = 15.dp, bottom = 56.dp)
           .fillMaxWidth()
           .wrapContentHeight()
           .constrainAs(Restaurants) {

               top.linkTo(RestaurantsNameRef.bottom)
               start.linkTo(parent.start)
               end.linkTo(parent.end)

           }
       ) {
           GetAllRestaurants(navController = navController)
       }
    }
}

@Composable
fun RestaurantCount(restaurantsMvvm : RestaurantsViewModel = viewModel()){
                    restaurantsMvvm.getAllRestaurants()
    val restaurantsList by  restaurantsMvvm.restaurantsLiveData.observeAsState(initial =  Collections.emptyList())
    Text(
        text       = "Restaurants" ,
        fontWeight = FontWeight.Bold,

        )
    Spacer(modifier = Modifier.width(5.dp))
    Text(text = "(")
    Text(text = "${restaurantsList.size}")
    Text(text = ")")
}

@Composable
fun GetAllRestaurants(restaurantsMvvm : RestaurantsViewModel = viewModel() , navController: NavController)
{
    restaurantsMvvm.getAllRestaurants()
    val restaurantsList by  restaurantsMvvm.restaurantsLiveData.observeAsState(initial =  Collections.emptyList())

    LazyColumn{
        items(restaurantsList){
            RestaurantsRow(data = it , navController)
        }
    }
}

@Composable
fun RestaurantsRow(data : RestaurantsData , navController: NavController){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 10.dp, bottom = 56.dp)
            .clickable {
                navController.navigate(
                    "RestaurantsDetailsScreen?"
                            + "resName=${data.name}"
                            + "&resId=${data.id}"
                            + "&location=${data.location}"
                            + "&description=${data.description}"
                            + "&time=${data.time}"
                            + "&deliverFee=${data.deliveryFee}"
                            + "&resLogo=${data.resLogo}"
                            + "&imgBackground=${data.imgBackground}"
                )
            }
        ,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color(0xFFFFFFFF),

    ) {

      ConstraintLayout(
          modifier = Modifier
              .fillMaxWidth()
              .wrapContentHeight())
      {
          val (imageMealRef  ,
              boxSaleRef     ,
              imageRes       ,
              nameResRef     ,
              descriptionRef ,
              ratingBarRef   ,
              giftRef,
              viewRef,
              deliveryRef   

          ) = createRefs()

         GlideImage( //  start Image Meal
              imageModel = data.mealImg,
              modifier = Modifier
                  .fillMaxWidth()
                  .height(200.dp)
                  .constrainAs(imageMealRef)
                  {
                      top.linkTo(parent.top)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)
                  })//  end Image Meal

          Box(  // Start Best Selling
              contentAlignment = Alignment.Center,
              modifier = Modifier
                  .padding(bottom = 20.dp, start = 20.dp)
                  .clip(RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp))
                  .background(Color(0x8DFFFFFF))
                  .width(180.dp)
                  .height(45.dp)
                  .constrainAs(boxSaleRef) {
                      bottom.linkTo(imageMealRef.bottom)
                  }
          ) {
               Row(
                   verticalAlignment     = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
               ) 
               { 
                  Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                      Icon(
                          painter   = painterResource(id = R.drawable.crown),
                          contentDescription = "Crown",
                          tint = Yellow500

                      )
                      Spacer(modifier = Modifier.width(5.dp))
                      Text(
                          text = "Best Selling",
                          color =  Color(0xC6FFFFFF)
                      )
                  }

                   Spacer(modifier = Modifier.width(30.dp))
                   
                   Box(
                       modifier = Modifier
                           .clip(CircleShape)
                           .background(Color(0xFFD6D6D6))
                           .size(20.dp),
                       contentAlignment = Alignment.Center
                   ) {
                       Icon(
                           painter = painterResource(id = R.drawable.arrowright),
                           contentDescription = "",
                           tint = Color.White
                       )
                   }
               }
          }   // End Best Selling

               GlideImage(  // Start Logo Restaurant
                    imageModel = data.resLogo,
              modifier =
              Modifier
                  .padding(top = 20.dp, start = 20.dp)
                  .clip(CircleShape)
                  .size(70.dp)
                  .constrainAs(imageRes) {
                      top.linkTo(imageMealRef.bottom)
                      start.linkTo(parent.start)

                  }) // Start Logo Restaurant


          Text(
              text = data.name,
              fontFamily  = Typography.body1.fontFamily,
              modifier   = Modifier
                  .padding(top = 20.dp, start = 10.dp)
                  .constrainAs(nameResRef) {
                      top.linkTo(imageRes.top)
                      start.linkTo(imageRes.end)
                  })

          Text(
              text       = data.description,
              fontFamily = Typography.h1.fontFamily,
              modifier   = Modifier
                  .padding(start = 10.dp)
                  .constrainAs(descriptionRef) {
                      top.linkTo(nameResRef.bottom)
                      start.linkTo(nameResRef.start)
                  },
              fontSize = 12.sp
          )

          Icon(
              painter = painterResource(id = R.drawable.ratingbar),
              contentDescription = "Rating bar",
              tint = Yellow500,
              modifier =
              Modifier
                  .padding(end = 20.dp, top = 10.dp)
                  .constrainAs(ratingBarRef)
                  {
                      top.linkTo(imageMealRef.bottom)
                      end.linkTo(parent.end)
                  }
          )

          Box(  // Start Gift Cart
              modifier = Modifier
                  .padding(start = 20.dp, top = 10.dp)
                  .clip(RoundedCornerShape(10.dp))
                  .wrapContentWidth()
                  .height(30.dp)
                  .background(Yellow200)
                  .padding(7.dp)
                  .constrainAs(giftRef)
                  {
                      top.linkTo(imageRes.bottom)
                      start.linkTo(parent.start)
                  }
              ,
              contentAlignment = Alignment.Center
          )
          {
          Row(
              verticalAlignment     = Alignment.CenterVertically ,
              horizontalArrangement = Arrangement.SpaceBetween)
          {

                Icon(
                    painter = painterResource(id = R.drawable.giftcard),
                    contentDescription =  "Gift Cart Icon",
                    modifier           = Modifier.size(15.dp),
                    tint = Yellow500
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = "Pizza with Friesz & Sauce for "   , fontSize = 10.sp , fontFamily = Typography.h1.fontFamily)
                Text(text =  data.mealPrice , fontSize = 10.sp , fontFamily = Typography.h1.fontFamily)
                Text(text = " L.E",  fontSize = 10.sp , fontFamily = Typography.h1.fontFamily)
          }

          } // End Gift Cart

          Row(  // Start View
              modifier = Modifier
                  .padding(top = 5.dp, bottom = 5.dp)
                  .fillMaxWidth()
                  .height(1.dp)
                  .background(Color(0x57090909))
                  .constrainAs(viewRef)
                  {
                      top.linkTo(giftRef.bottom)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)
                  }){}

          Row(
              verticalAlignment     = Alignment.CenterVertically ,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier
                  .padding(10.dp)
                  .fillMaxWidth()
                  .constrainAs(deliveryRef)
                  {
                      top.linkTo(giftRef.bottom)
                      start.linkTo(parent.start)
                      end.linkTo(parent.end)
                  })
          {

            Row (verticalAlignment     = Alignment.CenterVertically){
               Image(
                   painter            = painterResource(id = R.drawable.schedule),
                   contentDescription = "time",
               ) 
                Spacer(modifier = Modifier.width(5.dp))
                Text(text =  data.time , fontFamily = Typography.h1.fontFamily)
                Text(text = " mins", color = Yellow500 , fontFamily = Typography.h1.fontFamily)
            }

              Row (verticalAlignment     = Alignment.CenterVertically){
                  Icon(
                      painter            = painterResource(id = R.drawable.motor),
                      contentDescription = "time",
                      tint = Yellow500
                  )
                  Spacer(modifier = Modifier.width(5.dp))
                  Text(text =  data.deliveryFee , fontFamily = Typography.h1.fontFamily)
                  Text(text = " EGP" , color = Yellow500 , fontFamily = Typography.h1.fontFamily)
              }
          }
      }
    }
}