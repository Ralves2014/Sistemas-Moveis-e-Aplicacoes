package com.example.studyplantasker

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.example.studyplantasker.databinding.ActivityMainBinding
import android.support.v7.widget.RecyclerView
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var log_user: String
    override fun onCreate(savedInstanceState: Bundle?) {

        val intent = intent

        val username = intent.getStringExtra("message_key")
        log_user = username.toString()

        Log.d("tag",username.toString())

        val intent1 = Intent(this, Add_Task::class.java)

        intent1.putExtra("key", username)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(todo())

        val db = DBTAsks(this)
        val alltasks= db.selectallTasks()
        var adapter = TaskAdapter(alltasks)


        binding.exitapp.setOnClickListener{
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
        binding.seetasks.setOnClickListener {
            val intent4 = Intent(this, MainActivity::class.java)
            intent4.putExtra("message_key", username)
            startActivity(intent4)
        }
        binding.addtask.setOnClickListener {
            startActivity(intent1)
        }

        binding.todobtn.setOnClickListener {
            replaceFragment(todo())
        }
        binding.doingbtn.setOnClickListener {
            replaceFragment(doing())
        }
        binding.donebtn.setOnClickListener {
            replaceFragment(done())
        }
    }
    fun getEditTextValue(): String {
        return log_user
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}