package mhmd.salem.coffe.data

data class RestaurantDetailsData (
        val id       :String,
        val mealName :String,
        val mealImg  :String,
        val oldPrice :Double,
        val newPrice :Double,
        val mealDescription :String
        )
{
    constructor():this("","" , "" , 0.00 , 0.00 ,"")
}
