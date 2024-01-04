package com.example.car_showroom.SaleDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "sales")
data class Sale (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "photo")
    var photo: String,
    @ColumnInfo(name = "nameCar")
    var nameCar: String,
    @ColumnInfo(name = "user")
    var user: String,
    @ColumnInfo(name = "status")
    var status: Int,
    @ColumnInfo(name = "id_car")
    var id_car: Int
)