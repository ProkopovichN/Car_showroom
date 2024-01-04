package com.example.car_showroom.CarsDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoCars {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(cars: Cars)
    @Query("SELECT * FROM cars")
    fun getAllItems(): List<Cars>
    @Query("DELETE FROM cars WHERE id = :id")
    fun delete(id : Int)
}