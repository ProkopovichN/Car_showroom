package com.example.car_showroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.car_showroom.UsersDatabase.Users
import com.example.car_showroom.UsersDatabase.UsersDb
import com.example.car_showroom.databinding.ActivityRegistrationBinding
import kotlin.concurrent.thread

class Registration : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = UsersDb.getDb(this)
        binding.regButton.setOnClickListener(){
            thread {
                if (!TextUtils.isEmpty(binding.regName.text.toString()) &&
                    !TextUtils.isEmpty(binding.regSurname.text.toString()) &&
                    !TextUtils.isEmpty(binding.regEmail.text.toString()) &&
                    !TextUtils.isEmpty(binding.regPassword.text.toString())
                ) {
                    if (db.getDao().checkAccount(binding.regEmail.toString()) == 1) {
                        //showText("Аккаунт уже зарегистрирован")
                    } else {
                        db.getDao().insertItem(Users(binding.regEmail.text.toString(),
                            binding.regPassword.text.toString(),
                            binding.regName.text.toString(),
                            binding.regSurname.text.toString(),
                            "null",
                            "null"))
                        val intent = Intent(this, SignIn::class.java)
                        startActivity(intent)

                    }
                } else {
                    //showText("Есть пустое поле")
                }
            }
        }
    }

    fun showText(string: String){
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }
}