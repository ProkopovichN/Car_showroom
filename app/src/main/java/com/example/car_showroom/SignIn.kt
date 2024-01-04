package com.example.car_showroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.car_showroom.Admin.MainActivityAdmin
import com.example.car_showroom.UsersDatabase.Users
import com.example.car_showroom.UsersDatabase.UsersDb
import com.example.car_showroom.databinding.ActivitySignInBinding
import kotlin.concurrent.thread

class SignIn : AppCompatActivity() {
    private lateinit var binding : ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = UsersDb.getDb(this)

        binding.buttonSignin.setOnClickListener(){
            thread {
                if (TextUtils.isEmpty(binding.textLogin.text.toString()) || TextUtils.isEmpty(binding.textPassword.text.toString())) {
                    runOnUiThread {
                        binding.textIncorrect.visibility = (View.VISIBLE)
                    }
                }
                else if (!TextUtils.isEmpty(binding.textLogin.text.toString()) && !TextUtils.isEmpty(binding.textPassword.text.toString())){
                    if (db.getDao().checkAccount(binding.textLogin.text.toString()) == 1 &&
                        (db.getDao().retPassword(binding.textLogin.text.toString()).password == binding.textPassword.text.toString())){
                        if (binding.textLogin.text.toString() != "admin") {
                            val intent = Intent(this, MainActivity::class.java).apply {
                                putExtra("log", binding.textLogin.text.toString())
                                putExtra("name", db.getDao().retPassword(binding.textLogin.text.toString()).name)
                                putExtra("surn", db.getDao().retPassword(binding.textLogin.text.toString()).surname)
                            }
                            runOnUiThread {
                                binding.textIncorrect.visibility = (View.GONE)
                            }
                            startActivity(intent)
                        }
                        else{
                            runOnUiThread {
                                binding.textIncorrect.visibility = (View.GONE)
                            }
                            val intent = Intent(this, MainActivityAdmin::class.java)
                            startActivity(intent)
                        }
                    }
                    else {
                        runOnUiThread {
                            binding.textIncorrect.visibility = (View.VISIBLE)
                        }
                    }
                }
            }
        }

        binding.buttonReg.setOnClickListener(){
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
    }
}