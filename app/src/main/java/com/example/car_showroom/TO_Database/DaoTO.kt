package com.example.car_showroom.TO_Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DaoTO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(to: To)
    @Query("SELECT * FROM `to1` WHERE date = :date")
    fun getAllItems(date : String): List<To>
    @Query("SELECT COUNT(*) FROM `to1` WHERE login = :login")
    fun checkTO(login : String): Int
    @Query("SELECT * FROM `to1` WHERE login = :login")
    fun getItem(login : String): To
    @Query("SELECT * FROM `to1`")
    fun getAllItem(): List<To>
    @Query("UPDATE to1 SET status = :st WHERE Login = :login")
    fun updateStateTO(st : Int, login : String)
    @Query("DELETE FROM to1 WHERE login = :login")
    fun delete(login : String)
}