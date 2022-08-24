package mhmd.salem.coffe.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName =  "drinksInformation")
data class DrinksData(
    val firstName    :String,
    val secondName   :String,
    val background   :String,
    val energy       :String,
    @PrimaryKey
    val id           :String,
    val imgUrl       :String,
    val natural      :String,
    val price        :String,
    val vitamin      :String,
    val carbohydrate :String,
    val water :String,
    val details :String

)
{
    constructor():this(
          "",
        "",
        "",
              "",
            "",
        "",
        "",
        "",
        "",
        "",
        "",
        ""

    )
}

