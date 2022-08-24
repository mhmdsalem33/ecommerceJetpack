package mhmd.salem.coffe.data

data class RestaurantsData(
       val id            :String,
       val name          :String,
       val resLogo       :String,
       val mealImg       :String,
       val time          :String,
       val description   :String,
       val deliveryFee   :String,
       val mealPrice     :String,
       val location      :String,
       val imgBackground :String
       )
{
    constructor() :this("" , "" ,"","","","","","","" ,"")
}
