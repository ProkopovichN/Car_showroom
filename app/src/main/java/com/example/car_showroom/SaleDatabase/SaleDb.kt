package com.example.car_showroom.SaleDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.car_showroom.TO_Database.DaoTO
import com.example.car_showroom.SaleDatabase.Sale

@Database (entities = [Sale::class], version = 1)
abstract class SaleDb : RoomDatabase() {
    abstract fun getDao(): DaoSale

    companion object{
        fun getDb(context: Context): SaleDb{
            return Room.databaseBuilder(
                context.applicationContext,
                SaleDb::class.java,
                "Sale_db1.db"
            ).build()
        }
    }
}