package com.example.car_showroom.CarsDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Cars::class], version = 1)
abstract class CarsDb : RoomDatabase() {
    abstract fun getDao(): DaoCars

    companion object{
        fun getDb(context: Context): CarsDb{
            return Room.databaseBuilder(
                context.applicationContext,
                CarsDb::class.java,
                "cars.db"
            ).build()
        }
    }
}