package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens.BurgerMeal
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens.GetAllBurgers

import mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens.HamburgerScreen
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.CategoriesScreens.PizzaScreen
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.RestaurantsDetailsScreen.RestaurantsDetailsScreen


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = Screen.Home.route ){

        composable(Screen.Home.route)
        {
                HomeScreen(navController = navController)
        }
        composable(Screen.Favorite.route)
        {
            Favorite(navController =  navController)
        }
        composable(Screen.Cart.route)
        {
            Cart(navController = navController)
        }
        composable(Screen.Setting.route){
            Profile(navController = navController)
        }

        composable(
            route     = "detailsScreen"   +
                        "?name={name}"    +
                        "&ImgUrl={ImgUrl}"+
                        "&id={id}",
            arguments = listOf(
                navArgument(name = "name"){
                    type = NavType.StringType
                    defaultValue = "Mohamed"
                },
                navArgument(name = "ImgUrl"){
                    type     = NavType.StringType
                    nullable = true
                },
                navArgument(name = "id"){
                    type     = NavType.StringType
                    nullable = true
                }
            )
        ){  backStackEntry ->
            DetailsScreen(
                navController ,
                name   =   backStackEntry.arguments?.getString("name"),
                ImgUrl =   backStackEntry.arguments?.getString("ImgUrl"),
                Id     =   backStackEntry.arguments?.getString("id") )
        }

        //Categories Screens
        composable("Drinks"){
            DrinksScreen(navController = navController)
        }

        composable("Pizza"){
            PizzaScreen(navController = navController)
        }

        composable("Hamburger")
        {
            HamburgerScreen(navController = navController)
        }

        composable(
            route= "RestaurantsDetailsScreen?"
                    + "resName={resName}"
                    + "&resId={resId}"
                    + "&location={location}"
                    + "&description={description}"
                    + "&time={time}"
                    + "&deliverFee={deliverFee}"
                    + "&resLogo={resLogo}"
                    + "&imgBackground={imgBackground}"
            ,
            arguments = listOf(

                    navArgument(name = "resName")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "resId")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "location")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "description")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "time")
                    {
                         type = NavType.StringType
                         defaultValue = ""
                    },
                     navArgument(name = "deliverFee")
                     {
                         type = NavType.StringType
                         defaultValue = ""
                     },
                    navArgument(name = "resLogo")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    },
                    navArgument(name = "imgBackground")
                    {
                        type = NavType.StringType
                        defaultValue = ""
                    }
            )
        )
        {
            RestaurantsDetailsScreen(
                resName       = it.arguments?.getString("resName"),
                resId         = it.arguments?.getString("resId"),
                location      = it.arguments?.getString("location"),
                description   = it.arguments?.getString("description"),
                time          = it.arguments?.getString("time"),
                deliveryFee   = it.arguments?.getString("deliverFee"),
                resLogo       = it.arguments?.getString("resLogo"),
                imgBackground = it.arguments?.getString("imgBackground"),
                navController = navController)
        }

        composable(route =   "DrinksDetailsScreen?" +
                "firstName={firstName}"    +
                "&secondName={secondName}" +
                "&background={background}" +
                "&energy={energy}"         +
                "&id={id}"                 +
                "&imgUrl={imgUrl}"         +
                "&natural={natural}"       +
                "&price={price}"           +
                "&vitamin={vitamin}"       +
                "&carbohydrate={carbohydrate}"+
                "&water={water}"              +
                "&details={details}",
            arguments = listOf(

                navArgument(name = "firstName")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "secondName")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "background")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "energy")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "id")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "imgUrl")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
                ,
                navArgument(name = "natural")
                {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument(name = "price")
                {
                    type = NavType.StringType
                    defaultValue = "0"
                }
                ,
                navArgument(name = "vitamin")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
                ,
                navArgument(name = "carbohydrate")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
                ,
                navArgument(name = "water")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }
                ,
                navArgument(name = "details")
                {
                    type = NavType.StringType
                    defaultValue = ""
                }


            )

            )
        { backStackEntry ->
            DrinksDetailsScreen(
                firstName      = backStackEntry.arguments?.getString("firstName")   ,
                secondName     = backStackEntry.arguments?.getString("secondName")  ,
                background     = backStackEntry.arguments?.getString("background")  ,
                energy         = backStackEntry.arguments?.getString("energy")      ,
                id             = backStackEntry.arguments?.getString("id")          ,
                imgUrl         = backStackEntry.arguments?.getString("imgUrl")      ,
                natural        = backStackEntry.arguments?.getString("natural")     ,
                price          = backStackEntry.arguments?.getString("price")       ,
                vitamin        = backStackEntry.arguments?.getString("vitamin")     ,
                carbohydrate   = backStackEntry.arguments?.getString("carbohydrate"),
                water          = backStackEntry.arguments?.getString("water")       ,
                description    = backStackEntry.arguments?.getString("details")
            , navController = navController )
        }

        composable("mealBurger")
        {
            BurgerMeal()
        }

        composable("placeOrder")
        {
            PlaceOrderScreen(navController = navController)
        }
        composable("searchScreen")
        {
            SearchScreen(navController = navController)
        }
    }
}