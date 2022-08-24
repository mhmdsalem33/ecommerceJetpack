package mhmd.salem.coffe.data

data class MealData (
    var id     :String,
    var name   :String,
    var price  :Double,
    var ImgUrl :String,
    var rating :String)
{
   constructor() :this("" , "" ,0.00 , "" , "" )
}


