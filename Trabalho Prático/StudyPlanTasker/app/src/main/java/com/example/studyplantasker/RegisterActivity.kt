package com.example.studyplantasker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.studyplantasker.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(){

    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitLogin.setOnClickListener {
            val db = DBLogin(this)
            val allusers = db.selectall()

            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            val res = db.userInsert(username,password)

            if (res > 0){
                allusers.add(Utilizador(res.toInt(), username, password))
            }
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}