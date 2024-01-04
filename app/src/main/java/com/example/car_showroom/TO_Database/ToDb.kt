package com.example.car_showroom.TO_Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [To::class], version = 1)
abstract class ToDb : RoomDatabase() {
    abstract fun getDao(): DaoTO

    companion object{
        fun getDb(context: Context): ToDb{
            return Room.databaseBuilder(
                context.applicationContext,
                ToDb::class.java,
                "T0_d1.db"
            ).build()
        }
    }
}