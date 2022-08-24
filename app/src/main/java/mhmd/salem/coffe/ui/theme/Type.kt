package mhmd.salem.coffe.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mhmd.salem.coffe.R

// Set of Material typography styles to start with




val fonts = FontFamily(
    fonts = listOf(
        Font(R.font.popins_regular , style = FontStyle.Normal),
        Font(R.font.poppins_medium , style = FontStyle.Normal , weight = FontWeight.Medium),
        Font(R.font.poppins_semibold , style = FontStyle.Normal , weight = FontWeight.SemiBold)

    )
)

val MyCustomFont = FontFamily(
    Font(R.font.ads,FontWeight.Bold)
)

val buttonFont  = FontFamily(
    Font(R.font.ad , FontWeight.Bold)
)

val landFont = FontFamily(
    Font(R.font.myfont)
)
val Typography = Typography(


    h1 = TextStyle(
        fontFamily = landFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    h2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 26.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),



    body1 = TextStyle(
        fontFamily = MyCustomFont,
        fontWeight = FontWeight.Normal,
        fontSize   = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = buttonFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),




    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)