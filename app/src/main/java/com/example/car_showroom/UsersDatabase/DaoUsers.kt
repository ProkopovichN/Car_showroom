package com.example.car_showroom.UsersDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoUsers {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(user: Users)
    @Query("UPDATE users1 SET marka_auto = :marka WHERE Login = :login")
    fun updateMarkaUserCar(login : String, marka : String)
    @Query("UPDATE users1 SET model_auto = :model WHERE Login = :login")
    fun updateModelUserCar(login : String, model : String)
    @Query("SELECT COUNT(*) FROM users1 WHERE Login = :login")
    fun checkAccount(login : String): Int
    @Query("SELECT * FROM users1 WHERE Login = :login")
    fun retPassword(login : String): Users
}