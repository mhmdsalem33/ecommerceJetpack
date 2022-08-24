package mhmd.salem.coffe.data

data class UserData(
    var id    :String,
    var name  :String,
    var email :String,
    var profileImg :String
){
    constructor():this("" , "","" ,"")
}
