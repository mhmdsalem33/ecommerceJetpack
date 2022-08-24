package mhmd.salem.coffe.Room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import mhmd.salem.coffe.data.DrinksData


@Dao
interface DrinksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(drinks: ArrayList<DrinksData>)

    @Delete
    suspend fun delete(drinks: DrinksData)

    @Query("SELECT * FROM drinksInformation")
    fun getAllDrinksSaved()  : Flow<List<DrinksData>>

    @Query("DELETE  FROM  drinksInformation")
    suspend fun  clear()

}