package mhmd.salem.coffe.MostScreensWithBottomNavigationView


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cemreonur.ub10_youtube.ui.theme.Yellow500
import com.skydoves.landscapist.glide.GlideImage
import mhmd.salem.coffe.R
import mhmd.salem.coffe.ViewModels.SearchViewModel
import mhmd.salem.coffe.data.HamburgerData


@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun SearchScreen(searchMvvm :SearchViewModel = viewModel() , navController: NavController)
{
    val keyBoardController  =  LocalSoftwareKeyboardController.current
    val focusManager        =  LocalFocusManager.current
    val context             =  LocalContext.current

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(top = 20.dp, start = 20.dp))
    {
        val (BoxRef, cartRef) = createRefs()
        var searchText by rememberSaveable{ mutableStateOf("")}

        Row(modifier = Modifier
            .constrainAs(BoxRef)
            {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            },
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Box(modifier = Modifier
                .padding(end = 10.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(40.dp)
                .background(Yellow500)
                .clickable {
                    navController.navigate(Screen.Home.route)
                    {
                        navController.popBackStack()
                    }
                },
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

            TextField(
                value         =  searchText       ,
                onValueChange = {searchText = it},
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor          = Color(0xD3E0E0E0),
                    textColor                = Color.Black,
                    unfocusedIndicatorColor  = Color.Transparent,
                    focusedIndicatorColor    = Color.Transparent,
                    cursorColor              = Color.Black,
                    unfocusedLabelColor      = Color.Black,
                    focusedLabelColor        = Color.Black

                ),
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clip(RoundedCornerShape(10.dp)),
                singleLine = true,
                label = { Text(text = "Search ...")},
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ })
                    {
                        Icon(imageVector = Icons.Default.Search, contentDescription = " Search Icon " )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction =  ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                            keyBoardController?.hide()
                            focusManager.clearFocus()
                        searchMvvm.searchInFirestore(searchText , context = context)
                    }
                )
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 30.dp)
                .constrainAs(cartRef)
                {

                    top.linkTo(BoxRef.bottom)
                    start.linkTo(parent.start)

                })
        {
            PrepareCartSearch(navController = navController)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun PrepareCartSearch(searchMvvm :SearchViewModel = viewModel() , navController: NavController)
{
    val search = searchMvvm.searchStateFlow.collectAsState(initial =  emptyList())

    LazyVerticalGrid(columns = GridCells.Adaptive(120.dp))
    {
        items(search.value)
        {
            CartRow(it , navController = navController)
        }
    }
}

@Composable
fun CartRow(data: HamburgerData , navController: NavController)
{
    Box(modifier = Modifier
        .padding(bottom = 10.dp, start = 20.dp , end = 20.dp)
        .clip(RoundedCornerShape(12.dp))
        .width(120.dp)
        .height(120.dp)
        .clickable {
            navController.navigate("Hamburger")
            {
                navController.popBackStack()
            }
        }
        .background(Color(0xFFF3F3F3)),
        contentAlignment = Alignment.Center)
    {
        GlideImage(
            imageModel   = data.imgUrl,
            modifier     = Modifier.size(70.dp),
            contentScale = ContentScale.Inside
        )
    }
}
