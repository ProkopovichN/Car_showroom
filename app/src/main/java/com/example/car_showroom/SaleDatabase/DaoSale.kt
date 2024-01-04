package com.example.car_showroom.SaleDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.car_showroom.SaleDatabase.Sale

@Dao
interface DaoSale {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(s: Sale)
    @Query("SELECT * FROM `sales`")
    fun getAllItems(): List<Sale>
    @Query("SELECT * FROM sales WHERE user = :login")
    fun getItem(login: String): Sale
    @Query("DELETE FROM sales WHERE user = :login")
    fun delete(login : String)
    @Query("UPDATE sales SET status = :st WHERE user = :login")
    fun updateStateSale(st : Int, login : String)
    @Query("SELECT COUNT(*) FROM sales WHERE user = :login")
    fun checkSale(login : String): Int
}