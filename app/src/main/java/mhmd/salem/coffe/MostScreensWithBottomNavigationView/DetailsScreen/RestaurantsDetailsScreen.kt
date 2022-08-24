package mhmd.salem.coffe.MostScreensWithBottomNavigationView.RestaurantsDetailsScreen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDecoration.Companion.LineThrough
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.CardItemBg
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.google.firebase.firestore.FirebaseFirestore
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.Screen


import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.RestaurantDetailsViewModel
import mhmd.salem.coffe.data.RestaurantDetailsData
import mhmd.salem.coffe.ui.theme.Typography
import mhmd.salem.coffe.ui.theme.fonts


private lateinit var restaurantId: String

@Composable
fun RestaurantsDetailsScreen(
    resName       :String? ,
    resId         :String? ,
    location      :String? ,
    description   :String? ,
    time          :String? ,
    deliveryFee   :String? ,
    resLogo       :String? ,
    imgBackground :String? ,
    navController :NavController,

)
{
    restaurantId = resId.toString()

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(top = 0.dp)
        .verticalScroll(rememberScrollState()))
    {
        val (
            topImg         ,
            imgLogo        ,
            arrowBack      ,
            resNameRef     ,
            descriptionRef ,
            locationRef    ,
            timeRef        ,
            offersRef      ,
            mealsRef
        ) = createRefs()

       GlideImage(
           imageModel = imgBackground, // ImgBackground Top
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .constrainAs(topImg)
                {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                } ,
            contentScale = ContentScale.Crop
        )

        Box(   // ARROW BACK
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(40.dp)
                .background(CardItemBg)
                .clickable {
                    navController.navigate("Pizza")
                    {
                        navController.popBackStack()
                    }
                }
                .constrainAs(arrowBack)
                {
                    top.linkTo(topImg.top)
                    start.linkTo(topImg.start)
                    bottom.linkTo(topImg.bottom)
                }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left),
                contentDescription = "arrowLeft",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
        }
              GlideImage(
                   imageModel = resLogo,
                   modifier   = Modifier
                       .padding(top = 20.dp, start = 20.dp)
                       .clip(CircleShape)
                       .size(70.dp)
                       .constrainAs(imgLogo)
                       {
                           top.linkTo(topImg.bottom)
                           start.linkTo(parent.start)
                       })

        Text( // Start Resturant Name
            text = resName.toString(),
            fontFamily = Typography.body1.fontFamily,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp)
                .constrainAs(resNameRef)
                {
                    start.linkTo(imgLogo.end)
                    top.linkTo(imgLogo.top)

                }
        )

        Text( // Start Resturant Name
            text       = description.toString(),
            fontFamily = Typography.h1.fontFamily,
            fontSize   = 12.sp,
            modifier   = Modifier
                .padding(start = 10.dp)
                .constrainAs(descriptionRef)
                {
                    top.linkTo(resNameRef.bottom)
                    start.linkTo(resNameRef.start)
                }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .constrainAs(locationRef)
                {
                    top.linkTo(imgLogo.bottom)
                    start.linkTo(parent.start)
                }
        )
        {
            Icon(
                painter   = painterResource(id = R.drawable.location),
                contentDescription = "Location",
                modifier  = Modifier.size(18.dp),
                tint      = Yellow500
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text        = location.toString() ,
                fontFamily  = Typography.h1.fontFamily,
                fontSize    = 12.sp
            )
        }

        Row(  // START TIME AND DELIVER
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 5.dp, start = 10.dp)
                .constrainAs(timeRef)
                {
                    top.linkTo(locationRef.bottom)
                    start.linkTo(parent.start)
                }
        )
        {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDDDDDD))
                    .wrapContentWidth()
                    .padding(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.motor),
                        contentDescription = "Motor",
                        modifier =  Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = time.toString(), fontFamily = Typography.h1.fontFamily , fontSize = 10.sp)
                    Text(text = " EGP", fontFamily = Typography.h1.fontFamily , fontSize = 10.sp)
                }

            }
            Spacer(modifier = Modifier.width(10.dp))

            Box(
                contentAlignment = Alignment.Center ,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFDDDDDD))
                    .wrapContentWidth()
                    .padding(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Image(painter = painterResource(id = R.drawable.schedule), contentDescription = "Motor" )
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = deliveryFee.toString()   , fontFamily = Typography.h1.fontFamily , fontSize = 10.sp)
                    Text(text = " mins" , fontFamily = Typography.h1.fontFamily , fontSize = 10.sp)
                }
            }
        } // End TIME AND DELIVER

        Text(  // STart Offers
            text = "Offers" ,
            fontFamily = Typography.h1.fontFamily ,
            fontWeight = FontWeight.Bold,
            fontSize   = 15.sp,
            modifier   = Modifier
                .padding(top = 10.dp, start = 20.dp)
                .constrainAs(offersRef)
                {
                    top.linkTo(timeRef.bottom)
                    start.linkTo(parent.start)
                }) // End offers


            Box(
                modifier = Modifier
                    .padding(top = 20.dp, bottom = 56.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .constrainAs(mealsRef)
                    {
                        top.linkTo(offersRef.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                GetAllRestaurantsDetails()
            }
    }
}

@Composable
fun GetAllRestaurantsDetails(){

    val restaurantListLiveDaa  = MutableLiveData<List<RestaurantDetailsData>>()
    val restaurantList :LiveData<List<RestaurantDetailsData>> = restaurantListLiveDaa

    FirebaseFirestore.getInstance().collection("Restaurants")
        .document(restaurantId)
        .collection("meals")
        .addSnapshotListener{values , error ->
            if (error != null)
            {
                Log.d("testApp" , error.message.toString() )
            }
            else
            {
                val resList  = arrayListOf<RestaurantDetailsData>()
                val resModel = values?.toObjects(RestaurantDetailsData::class.java)
                    resList.addAll(resModel!!)
                restaurantListLiveDaa.value = resList
            }
        }

   val data by restaurantList.observeAsState(initial =  emptyList())

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp),

    ){
        items(data)
        {
            MealsRow(data = it)
        }
    }

}


@Composable
fun MealsRow(data :RestaurantDetailsData ,  resDetailsMvvm : RestaurantDetailsViewModel = viewModel() )
{

    val context = LocalContext.current

   Card(modifier = Modifier
       .fillMaxWidth()
       .wrapContentHeight()
       .background(Color(0xEEE4E4E4))
   )
   {
       Row(
           horizontalArrangement = Arrangement.SpaceBetween  ,
           verticalAlignment     = Alignment.CenterVertically,
           modifier = Modifier
               .padding(10.dp)
               .fillMaxWidth()

       ) {   // Start Meal Details
           Column {

               Text(
                   text       = data.mealName,
                   fontFamily = Typography.h1.fontFamily,
                   fontSize   = 15.sp
               )
               Text(
                   text      = data.mealDescription,
                   fontSize  = 12.sp,
                   color     = Color(0xDABDBDBD)
               )

               Row(
                   verticalAlignment = Alignment.CenterVertically,
                   modifier          = Modifier.padding(top = 10.dp , bottom = 10.dp)
               )
               {
                   Text(   //New Price
                       text        = data.newPrice.toString(),
                       fontFamily  = Typography.h1.fontFamily ,
                       fontSize    = 12.sp)


                   Spacer(modifier = Modifier.width(5.dp))

                   Text(text       = "EGP"   , fontFamily = Typography.h1.fontFamily , fontSize = 12.sp , color = Yellow500)

                   Spacer(modifier    = Modifier.width(15.dp))
                   Text(   // OLD PRICE
                       text           = data.oldPrice.toString(),
                       fontFamily     = Typography.h1.fontFamily ,
                       fontSize       = 12.sp,
                       textDecoration =  LineThrough
                   )
                   Spacer(modifier    = Modifier.width(5.dp))

                   Text(
                       text           = "EGP"   ,
                       fontFamily     = Typography.h1.fontFamily ,
                       fontSize       = 12.sp,
                       color          = Yellow500,
                       textDecoration = LineThrough )
               }

           }

            // todo Button add to cart
           Box(contentAlignment = Alignment.Center , modifier = Modifier
               .padding(top = 20.dp)) {
               Button(
                   onClick  = {
                       resDetailsMvvm.id            = data.id
                       resDetailsMvvm.productName   = data.mealName
                       resDetailsMvvm.description   = data.mealDescription
                       resDetailsMvvm.productImg    = data.mealImg
                       resDetailsMvvm.productPrice  = data.newPrice
                       resDetailsMvvm.totalPrice    = data.newPrice
                       resDetailsMvvm.totalQuantity = "1"
                       resDetailsMvvm.postRestaurantMealToFirestore(context = context)
                   },
                   modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                   colors   = ButtonDefaults.buttonColors(
                       backgroundColor = Color(0xFFFC6C26)
                   )
               )
               {
                   Icon(painter = painterResource(id = R.drawable.bag), contentDescription = "" , tint = Color.White )
               }
           }

           GlideImage(
               imageModel         = data.mealImg,
               modifier           = Modifier
                   .clip(CircleShape)
                   .size(70.dp),
               circularReveal    = CircularReveal(1000)
           )

       } // End Meal Details
   }
}
