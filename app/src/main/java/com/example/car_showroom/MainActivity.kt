package com.example.car_showroom

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.car_showroom.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    private lateinit var bind : ActivityMainBinding
    private val liveData: DataClass by viewModels()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        liveData.login.value = intent.getStringExtra("log")
        liveData.name.value = intent.getStringExtra("name")
        liveData.surname.value = intent.getStringExtra("surn")

        supportFragmentManager.beginTransaction()
            .replace(R.id.place, ListFragment.newInstance())
            .commit()
        bind.btnAuto.backgroundTintList = this.getResources().getColorStateList(R.color.red)
        bind.btnCab.backgroundTintList = this.getResources().getColorStateList(R.color.white)

        bind.btnAuto.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.place, ListFragment.newInstance())
                .commit()
            bind.btnAuto.backgroundTintList = this.getResources().getColorStateList(R.color.red)
            bind.btnCab.backgroundTintList = this.getResources().getColorStateList(R.color.white)
        }

        bind.btnCab.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.place, Profile.newInstance())
                .commit()
            bind.btnAuto.backgroundTintList = this.getResources().getColorStateList(R.color.white)
            bind.btnCab.backgroundTintList = this.getResources().getColorStateList(R.color.red)
        }
    }

    public fun repProfile(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.place, Profile.newInstance())
            .commit()
    }

}