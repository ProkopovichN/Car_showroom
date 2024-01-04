package com.example.car_showroom.UsersDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Users::class], version = 1)
abstract class UsersDb : RoomDatabase() {
    abstract fun getDao(): DaoUsers

    companion object{
        fun getDb(context: Context): UsersDb{
            return Room.databaseBuilder(
                context.applicationContext,
                UsersDb::class.java,
                "user1.db"
            ).build()
        }
    }
}