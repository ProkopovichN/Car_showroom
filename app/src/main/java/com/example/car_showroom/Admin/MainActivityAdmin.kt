package com.example.car_showroom.Admin

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.car_showroom.ListFragment
import com.example.car_showroom.R
import com.example.car_showroom.databinding.ActivityMainAdminBinding

class MainActivityAdmin : AppCompatActivity() {

    lateinit var bind : ActivityMainAdminBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(bind.root)
        bind.adminCarsBtn.setTextColor(this.getResources().getColorStateList(R.color.red))
        //replaceFragment(1)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, CarsAdmin.newInstance())
            .commit()


        bind.adminCarsBtn.setOnClickListener{
            bind.adminCarsBtn.setTextColor(this.getResources().getColorStateList(R.color.red))
            bind.adminSalesBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            bind.adminToBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            replaceFragment(1)
        }

        bind.adminSalesBtn.setOnClickListener{
            bind.adminCarsBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            bind.adminSalesBtn.setTextColor(this.getResources().getColorStateList(R.color.red))
            bind.adminToBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            replaceFragment(3)
        }

        bind.adminToBtn.setOnClickListener{
            bind.adminCarsBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            bind.adminSalesBtn.setTextColor(this.getResources().getColorStateList(R.color.white))
            bind.adminToBtn.setTextColor(this.getResources().getColorStateList(R.color.red))
            replaceFragment(2)
        }

    }

    fun replaceFragment(id : Int){
        if (id == 1) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, CarsAdmin.newInstance())
                .commit()
        }
        else if (id == 2){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, TOAdmin.newInstance())
                .commit()
        }
        else if (id == 3){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, SaleAdmin.newInstance())
                .commit()
        }
        else if (id == 4){
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame, AddCarAdmin.newInstance())
                .commit()
        }
    }
}