package mhmd.salem.coffe.Activites

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cemreonur.ub10_youtube.ui.theme.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import mhmd.salem.coffe.Activites.ui.theme.CoffeTheme
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.Navigation
import mhmd.salem.coffe.MostScreensWithBottomNavigationView.Screen
import mhmd.salem.coffe.Room.DrinksDatabase
import mhmd.salem.coffe.ViewModels.DrinksDetailsViewModel
import mhmd.salem.coffe.ViewModels.DrinksDetailsViewModelFactory


class MainActivity : ComponentActivity() {
    val drinksDetailsMvvm : DrinksDetailsViewModel by lazy {
        val database = DrinksDatabase.getInstance(this)
        val factory  = DrinksDetailsViewModelFactory(database)
        ViewModelProvider(this , factory)[DrinksDetailsViewModel::class.java]
    }
   // private val categoryMvvm : CategoriesViewModel by viewModels<CategoriesViewModel>()
   // private val popularMvvm  : PopularViewModel    by viewModels<PopularViewModel>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @ExperimentalComposeUiApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController  = rememberNavController()
            CoffeTheme {
                
            }
            val items = listOf(Screen.Home , Screen.Favorite , Screen.Cart , Screen.Setting)
            Scaffold(bottomBar = {
                            BottomNavigation(
                                modifier        = Modifier.clip(RoundedCornerShape(topStart = 30.dp , topEnd = 30.dp)),
                                backgroundColor = Yellow200
                            ) {
                                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                                    val currentRoute = navBackStackEntry?.destination?.route

                                items.forEach{ screen ->
                                    BottomNavigationItem(
                                        icon = { Icon(imageVector = screen.icon, contentDescription = "Icons" )},
                                        label = { Text(text = screen.title)},
                                        selected = currentRoute == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route){
                                                popUpTo(navController.graph.findStartDestination().id){
                                                    saveState = true
                                                }
                                                  launchSingleTop  =  true
                                                  restoreState     =  true
                                            }
                                        })
                                }
                            }
            }) {
             //   HomeScreen()
                Navigation(navController = navController)
            }
        }
    }
}

