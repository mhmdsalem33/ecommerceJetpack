package mhmd.salem.coffe.data

import java.io.Serializable

data class CartData(
    val id            :String,
    val Description   :String,
    val productImg    :String,
    val productName   :String,
    val productPrice  :Double,
    val totalPrice    :Double,
    val totalQuantity :String,

):Serializable{
    constructor():this("","","","",0.00,0.00 , "")
}
