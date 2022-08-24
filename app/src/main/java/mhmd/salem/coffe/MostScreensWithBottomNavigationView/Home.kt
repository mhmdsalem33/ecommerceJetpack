package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import com.cemreonur.ub10_youtube.ui.theme.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.Activites.SplashScreenActivity
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.CategoriesViewModel
import mhmd.salem.coffe.ViewModels.PopularViewModel
import mhmd.salem.coffe.data.CartData
import mhmd.salem.coffe.data.CategoryData
import mhmd.salem.coffe.data.MealData
import mhmd.salem.coffe.ui.theme.Typography
import java.util.*


class Homes: ComponentActivity(){


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


        setContent {}
    }
  }

@Composable
fun HomeScreen(navController: NavController){

    val scrollState = rememberScrollState()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(start = 30.dp, top = 48.dp, end = 17.dp, bottom = 56.dp)) // Main Box

    {
        Column(modifier = Modifier.verticalScroll( state = scrollState , enabled = true ) ) {  // Start Of Column
            Header(navController = navController)

            Spacer(modifier = Modifier.height(32.dp))

            OrderNowBox()

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text       = "Categories"  ,
                fontSize   = 22.sp         ,
                color      = BlackTextColor,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(20.dp))

            CategoryList(navController = navController)

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text       = "Popular"  ,
                fontSize   = 22.sp         ,
                color      = BlackTextColor,
                fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(10.dp))

          PopularList(navController = navController)

        } // End Of Column
    }
}

@Composable
fun Header(navController: NavController){
    Row(
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        BoxIconMenu(resIconId = R.drawable.menu , description = "Icon Menu")

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(
                painter    = painterResource(id = R.drawable.location),
                contentDescription = "Location" ,
                modifier   = Modifier.size(16.dp),
                tint       = Orange500
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "California, Us")
            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                painter = painterResource(id = R.drawable.arrow_down),
                contentDescription = "Arrow Down" ,
                modifier = Modifier.size(16.dp),
                tint = Orange500
            )
        }
        BoxIconSearch(searchIconId = R.drawable.search , description = "Icon Menu" , navController = navController)
    }
}

@Composable
fun BoxIconMenu(
    resIconId : Int ,
    description :String,
    bgColor   : Color ? = Color(0xFFF2F2F2) ,
    iconColor : Color ? = IconColor  ,
)
{
     val context = LocalContext.current
    Box(  // Box Menu Icon
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor!!)
            .clickable {

                 FirebaseAuth.getInstance().signOut()

                 val intent = Intent(context, SplashScreenActivity::class.java)
                     intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                 context.startActivity(intent)
            },
        contentAlignment = Alignment.Center

    ){
        Icon(
            // Icon Menu
            painter = painterResource(id = resIconId),
            contentDescription = description,
            modifier = Modifier
                .size(24.dp),
            tint = iconColor!!,

            )
    }
}

@Composable
fun BoxIconSearch(
    searchIconId : Int,
    description  : String,
    bgColor      : Color? = Color(0xFFF2F2F2),
    iconColor    : Color? = IconColor,
    navController: NavController

){

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor!!)
            .clickable
            {
                navController.navigate("searchScreen") {
                    navController.popBackStack()
                }
            }
        ,
        contentAlignment = Alignment.Center
    )
    {
        Icon(
            painter   = painterResource(id = searchIconId),
            contentDescription = description,
            modifier  = Modifier.size(24.dp),
            tint      = iconColor!!
        )
    }
}


@Composable
fun OrderNowBox(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(156.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Yellow200)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Column {   // Start Column

                Text(text = buildAnnotatedString {   // Start Text
                    withStyle(
                        style = SpanStyle(
                            color     =    BlackTextColor,
                            fontStyle =    Typography.body1.fontStyle,
                            fontFamily =   Typography.body1.fontFamily
                        ),
                    )
                    {
                        append("The Fastest In \n" + "Delivery ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color      = Yellow500,
                            fontFamily = Typography.body1.fontFamily
                        )
                    )
                    {
                        append("Food")
                    }
                }) // End Text

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .clip(RoundedCornerShape(15.dp))
                        .height(40.dp)
                        .width(126.dp),
                    colors =  ButtonDefaults.buttonColors(
                        backgroundColor = Yellow500
                    ))
                {
                    Text(
                        text = "Order Now",
                        fontFamily = Typography.button.fontFamily
                    )
                }
            } // End Column

            Image(
                painter = painterResource(id = R.drawable.man),
                contentDescription = "Delivery",
                modifier           = Modifier.size(156.dp)
            )
        }
    }
}

@Composable
fun CategoryList(categoryMvvm : CategoriesViewModel = viewModel() , navController: NavController){
                 categoryMvvm.getCategoryLiveData()
    val  categoryList by categoryMvvm.categoryLiveData.observeAsState(initial = Collections.emptyList())

    var selectedIndex = remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        items(categoryList.size){ index ->

                    CategoryRow(
                        categoryData   =  categoryList[index] ,
                        selectedIndex  =  selectedIndex    ,
                        index          =  index
                    , navController    = navController)
        }
    }
}

@Composable
fun CategoryRow(categoryData: CategoryData, selectedIndex : MutableState<Int>, index : Int , navController: NavController){
    Box(modifier = Modifier
        .padding(5.dp)
        .height(146.dp)
        .width(106.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable {

            selectedIndex.value = index
            navController.navigate(categoryData.title) {
                popUpTo(Screen.Home.route) {
                    inclusive = true // مهمة عشان يرجع من البوتن نافيجشن فيو
                }
            }
        }
        .background(
            if (selectedIndex.value == index) Yellow500 else CardItemBg
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            GlideImage(
                imageModel   = categoryData.ImgUrl ,
                contentScale = ContentScale.Inside,
                modifier     = Modifier
                    .size(50.dp)
                // .background(Color.White)
                ,
                colorFilter = if (selectedIndex.value == index) ColorFilter.tint(Color.White) else ColorFilter.tint(Color.Black),

                )

            //     Icon(painter = painterResource(id = R.drawable.man), contentDescription = "")
            Text(
                text  =     categoryData.title,
                color = if  (selectedIndex.value == index) Color.White else Color.Black
            )

        }
    }
}

@Composable
fun PopularList(popularMvvm :PopularViewModel = viewModel() , navController: NavController){
                popularMvvm.getPopularItems()
    val popularList by popularMvvm.popularLiveData.observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxWidth()) {
        for ( item in popularList)
        {
            Popular(item , navController = navController)
           // Log.d("testApp" , item.ingradients.toString())
        }
    }
}

@Composable
fun Popular(popular : MealData , navController :NavController){

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .padding(start = 10.dp, top = 20.dp, bottom = 3.dp)
        .clickable {
            navController.navigate(
                "detailsScreen?" +
                        "name=${popular.name}" +
                        "&ImgUrl=${popular.ImgUrl}" +
                        "&id=${popular.id}"

            ) { // لما يروح هينقلنى
                popUpTo(Screen.Home.route) { // وهو راجع
                    inclusive = true
                }
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {

                Box(contentAlignment = Alignment.Center , modifier = Modifier.height(40.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Image(
                            painter = painterResource(id = R.drawable.crown),
                            contentDescription ="Crown",
                            modifier = Modifier.size(25.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = "Best Selling" , color = Color(0xFEC7C7C7))

                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = popular.name , fontFamily = Typography.body1.fontFamily)

                Spacer(modifier = Modifier.width(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "$ " , color = Yellow500 ,  fontWeight = FontWeight.Bold)
                    Text(text = popular.price.toString() ,  fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {


                    Box(
                        modifier =
                        Modifier
                            .clip(RoundedCornerShape(topEnd = 15.dp, bottomStart = 15.dp))
                            .size(40.dp)
                            .background(Yellow500),
                        contentAlignment = Alignment.Center
                    )

                    {
                         Icon(
                             imageVector        = Icons.Default.Add,
                             contentDescription = "Add Icon Popular" ,
                             tint               = Color.White ,
                             modifier           = Modifier.size(24.dp))
                    }

                    Spacer(modifier = Modifier.width(20.dp))

                    Row (verticalAlignment = Alignment.CenterVertically){
                       Image(painter = painterResource(id = R.drawable.star), contentDescription = "Start" )
                       Spacer(modifier = Modifier.width(5.dp))
                       Text(text = popular.rating , fontWeight = FontWeight.Bold)
                    }
                }
            }
            Card(shape = CircleShape , elevation = 5.dp) {
                GlideImage(
                    imageModel = popular.ImgUrl,
                    modifier = Modifier.size(120.dp)//.clip(CircleShape),
                )
            }
        }
    }
}
