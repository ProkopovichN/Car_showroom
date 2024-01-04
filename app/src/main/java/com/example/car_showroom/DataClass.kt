package com.example.car_showroom

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class DataClass : ViewModel(){
    val login : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val name : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val surname : MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}