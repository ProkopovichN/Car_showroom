package com.example.car_showroom.UsersDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users1")
data class Users(
    @PrimaryKey(autoGenerate = false)
    var login: String,
    @ColumnInfo(name = "password")
    var password: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "surname")
    var surname: String,
    @ColumnInfo(name = "marka_auto")
    var marka_auto: String,
    @ColumnInfo(name = "model_auto")
    var model_auto: String,
)
