package mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Facebook
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.BackButton
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.Screen
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.HamburgerViewModel
import mhmd.salem.coffe.data.HamburgerData
import mhmd.salem.coffe.ui.theme.Typography


@Composable
fun HamburgerScreen(navController: NavController)
{
    BackButton(navController = navController )

    ConstraintLayout(
        modifier = Modifier
            .padding(20.dp, bottom = 56.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()))
    {

        val (arrowBackRef  , rowRef ) = createRefs()

        Row(modifier = Modifier
            .padding(end = 20.dp)
            .fillMaxWidth()
            .constrainAs(arrowBackRef)
        {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navController.navigate(Screen.Home.route)
                        {
                            navController.popBackStack()
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
                    painter = painterResource(id = R.drawable.arrow_left),
                    contentDescription = "Arrow back",
                    modifier = Modifier.size(24.dp)
                )
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .constrainAs(rowRef)
                {
                    top.linkTo(arrowBackRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Box(modifier = Modifier.padding(top = 50.dp, start = 15.dp))
            {

                GetAllBurgers(navController = navController)
            }
            Box()
            {
                BurgerMeal()
            }
        }
    }
}

@Composable
fun GetAllBurgers(burgerMvvm : HamburgerViewModel = viewModel() , navController: NavController)
{
        burgerMvvm.getALlHamburger()
    val hamburgerList by burgerMvvm.hamburgerLiveData.observeAsState(initial = emptyList())

    var selectedIndex = remember { mutableStateOf(0) }
    LazyColumn(Modifier.height(400.dp))
    {
        items(hamburgerList.size)
        { index ->
            AllBurgerMeals(
                data          =  hamburgerList[index] ,
                selectedIndex =  selectedIndex,
                index         =  index,
                navController = navController
           )
        }
    }
}

var mealList by  mutableStateOf<List<HamburgerData>>(emptyList())


@Composable
fun AllBurgerMeals(
    data : HamburgerData ,
    selectedIndex : MutableState<Int> ,
    index : Int,
    navController: NavController
)
{

    //todo show Data first time
    var firstShowData = remember { mutableStateOf("true") }
      if (firstShowData.value == "true")
      {
                 mealList = listOf(data)
      }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    //todo show Data when click
                    selectedIndex.value = index
                    firstShowData.value = "false"
                    mealList = listOf(data)
                }
                .background(
                    if (selectedIndex.value == index)
                        Color(0xFFEFEFEF)
                    else
                        Color.Transparent
                )
                .padding(15.dp)
        )
        {
       GlideImage(
                 imageModel = data.imgUrl,
                 modifier   = Modifier.size(70.dp)
            )
        }
}

@Composable
fun BurgerMeal(hamburgerMvvm : HamburgerViewModel = viewModel())
{

    Column(modifier = Modifier.padding(bottom = 26.dp , end = 10.dp), horizontalAlignment = Alignment.End)
    {
        for (i in mealList)
        GlideImage(
            imageModel =  i.imgUrl,
            modifier   = Modifier
                .height(250.dp)
                .width(150.dp)
        )

        Column(horizontalAlignment = Alignment.Start)
        {
            for (i in mealList)
            Text(
                text        =  i.name,
                fontFamily  = Typography.body1.fontFamily,
                fontSize    = 35.sp,
                modifier    = Modifier.padding(start = 40.dp)
            )
            for (i in mealList)
            Text(
                text         = i.secondName ,
                fontFamily   = Typography.body1.fontFamily,
                fontSize     = 25.sp,
                modifier     = Modifier.padding(start = 40.dp))
            for (i in mealList)
            Text(
                text        = i.weight,
                color       = (Color(0xFFB1B1B3)),
                modifier    = Modifier.padding(start = 40.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(modifier = Modifier.padding(start = 40.dp)) {

                Text(
                    text     = "$ "    ,
                    color    = (Color(0xFFF76E2E)) ,
                    fontSize = 12.sp)
                for (i in mealList)
                Text(
                    text       = i.price.toString() ,
                    color      = (Color(0xFFF76E2E)) ,
                    fontSize   = 18.sp ,
                    fontFamily = Typography.h1.fontFamily)
            }
            for (i in mealList)
            Text(
                text       =    i.description ,
                color      =    Color(0xFF6B6B6D),
                fontFamily =    Typography.h1.fontFamily,
                fontSize   =    11.sp,
                modifier   =    Modifier.padding(start = 40.dp , end = 10.dp),
                textAlign  =    TextAlign.Justify
            )
    val context = LocalContext.current
           Box(
               contentAlignment = Alignment.Center ,
               modifier         = Modifier
                   .fillMaxWidth()
                   .padding(top = 20.dp)) {
               Button(
                   onClick  = {
                       mealList.forEach { i ->
                           hamburgerMvvm.id            = i.id
                           hamburgerMvvm.productName   = i.name
                           hamburgerMvvm.totalPrice    = i.price
                           hamburgerMvvm.productImg    = i.imgUrl
                           hamburgerMvvm.productPrice  = i.price
                           hamburgerMvvm.description   = i.description
                           hamburgerMvvm.totalQuantity = "1"
                       }
                        hamburgerMvvm.postHamburgerMealToCart(context = context)

                   },
                   modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                   colors   = ButtonDefaults.buttonColors(
                       backgroundColor = Color(0xFFFC6C26)
                   )
               )
               {
                   Icon(painter = painterResource(id = R.drawable.bag), contentDescription = "" , tint = Color.White )
               }
           }
        }
    }

}



