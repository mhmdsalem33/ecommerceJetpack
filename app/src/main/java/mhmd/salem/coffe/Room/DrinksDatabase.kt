package mhmd.salem.coffe.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mhmd.salem.coffe.data.DrinksData

@Database(entities =  [DrinksData::class] , version = 1)
abstract class DrinksDatabase : RoomDatabase() {

    abstract val drinksDao  :DrinksDao

    companion object{
        @Volatile
        var INSTANCE : DrinksDatabase ? = null
        @Synchronized
        fun getInstance(context : Context) :DrinksDatabase
        {
            if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(
                    context,
                    DrinksDatabase::class.java,
                    "drinks_db"
                ).fallbackToDestructiveMigration()
                    .build()
            return INSTANCE as DrinksDatabase
        }
    }
}