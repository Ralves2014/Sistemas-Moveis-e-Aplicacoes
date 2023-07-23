package com.example.studyplantasker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setup()
    }

    private fun setup() {
        findViewById<Button>(R.id.submitLogin).setOnClickListener {
            if (areCredencialsValid()){
                val username = findViewById<EditText>(R.id.username).text.toString()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("message_key", username)
                startActivity(intent)
                finish()
            }
            else{
                findViewById<TextView>(R.id.erro).visibility = View.VISIBLE
            }
        }
        findViewById<Button>(R.id.regiterLogin).setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun areCredencialsValid(): Boolean {
        val username = findViewById<EditText>(R.id.username).text.toString()

        val db = DBLogin(this)

        val allusers = db.selectall()

        if (username.isEmpty()){
            findViewById<TextView>(R.id.erro).visibility = View.VISIBLE
            return false
        }
        val password = findViewById<EditText>(R.id.password).text.toString()
        if (password.isEmpty()){
            findViewById<TextView>(R.id.erro).visibility = View.VISIBLE
            return false
        }

        allusers.forEach {
            Log.d("ALLUSERS_USER", it.username)
            Log.d("ALLUSERS_ID", it.id.toString())
            Log.d("ALLUSERS_PASS", it.password)
            if (it.username == username && it.password == password){
                return true
            }
        }

        return false
    }
}

