package com.example.car_showroom.TO_Database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "to1")
data class To(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "login")
    var login: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "auto")
    var auto: String,
    @ColumnInfo(name = "time")
    var time: Int,
    @ColumnInfo(name = "status")
    var status: Int,
)
