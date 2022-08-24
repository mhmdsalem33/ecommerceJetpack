package mhmd.salem.coffe.LoginScreens

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login" )
    {
        composable(route = "login"){
            Login(navController)
        }

        composable(route = "register"){
            Register(navController)
        }
    }
}