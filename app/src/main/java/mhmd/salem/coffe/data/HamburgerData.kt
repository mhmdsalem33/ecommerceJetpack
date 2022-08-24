package mhmd.salem.coffe.data

data class HamburgerData(
    val id     :String,
    val imgUrl :String,
    val name   :String,
    val secondName :String,
    val price :Double,
    val description :String,
    val weight :String,
    val searchName :String
)
{
    constructor():this("" ,"" , "" , "" ,0.00 , "","" , "")
}
