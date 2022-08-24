package mhmd.salem.coffe.MostScreensWithBottomNavigationView

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route :String , val title :String , val icon : ImageVector)
{
    object Home : Screen(
         route  = "home",
         title  = "Home",
         icon   = Icons.Default.Home  )

    object Favorite : Screen(
        route   = "favorite",
        title   = "Favorite",
        icon    = Icons.Default.Favorite )

    object Cart : Screen(
        route   = "cart",
        title   = "Cart",
        icon    = Icons.Default.ShoppingCart )

    object Setting : Screen(
        route   = "settings",
        title   = "Profile",
        icon    = Icons.Default.Settings )
}