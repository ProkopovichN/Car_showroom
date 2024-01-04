package com.example.car_showroom.CarsDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cars")
data class Cars(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "photo")
    var photo: String,
    @ColumnInfo(name = "marka")
    var marka: String,
    @ColumnInfo(name = "model")
    var model: String,
    @ColumnInfo(name = "price")
    var price: Int,
    @ColumnInfo(name = "power")
    var power: Int,
    @ColumnInfo(name = "odometr")
    var odometr: String,
    @ColumnInfo(name = "year")
    var year: Int,
    @ColumnInfo(name = "privod")
    var privod: String,
    @ColumnInfo(name = "count_passengers")
    var c_passengers: Int,
    @ColumnInfo(name = "color")
    var color: String,
)
