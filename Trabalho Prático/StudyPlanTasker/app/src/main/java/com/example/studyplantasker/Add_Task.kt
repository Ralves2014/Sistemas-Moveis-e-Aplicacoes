package com.example.studyplantasker

import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.studyplantasker.databinding.ActivityAddTaskBinding

class Add_Task: AppCompatActivity()  {

    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var listState: ArrayList<String>
    private lateinit var spinChoice: String
    private lateinit var loginUSER: String
    private lateinit var dateLoginAdd: String
    private lateinit var descriptionLoginAdd: String
    private lateinit var titleLoginAdd: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val spinnerId = findViewById<Spinner>(R.id.spinnerState)

        //val allstates = arrayOf("TO DO", "DOING", "DONE")

        val username = intent.getStringExtra("key")
        loginUSER = username.toString()
        Log.d("ADD", username.toString())

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)


        listState = arrayListOf()
        listState.add("TO DO")
        listState.add("DOING")
        listState.add("DONE")

        val dataAdaptar = ArrayAdapter(this, android.support.constraint.R.layout.support_simple_spinner_dropdown_item, listState)

        dataAdaptar.setDropDownViewResource(android.support.design.R.layout.support_simple_spinner_dropdown_item)
        binding.spinnerState.adapter=dataAdaptar

        binding.spinnerState.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (parent!=null){
                    spinChoice = (parent.getChildAt(0) as TextView?)?.text as String
                    Log.d("SPINNER", spinChoice)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        binding.btinsertTask.setOnClickListener {
            titleLoginAdd = binding.titleAdd.text.toString()
            descriptionLoginAdd = binding.descriptionAdd.text.toString()
            dateLoginAdd = binding.dateAdd.text.toString()
            insertion()
        }

        binding.exitapp.setOnClickListener{
            moveTaskToBack(true)
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
        binding.seetasks.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            intent2.putExtra("message_key", username)
            startActivity(intent2)
        }
        binding.addtask.setOnClickListener {
            val intent5 = Intent(this, Add_Task::class.java)
            intent5.putExtra("key", username)
            startActivity(intent5)
        }

    }
    private fun insertion(){
        val db1 = DBLogin(this)
        val idLogUser = db1.selectByName(loginUSER)


        val db = DBTAsks(this)
        val alltasks = db.selectallTasks()
        Log.d("INSERT", titleLoginAdd)
        Log.d("INSERT", dateLoginAdd)
        Log.d("INSERT", descriptionLoginAdd)
        Log.d("INSERT", spinChoice)
        Log.d("INSERT", idLogUser.toString())
        if (titleLoginAdd.length >0 && descriptionLoginAdd.length >0 && dateLoginAdd.length >0){
            Log.d("ENTROU_INSERT", "ENTROU_INSERT")
            var res = db.taskInsert(idLogUser,dateLoginAdd,titleLoginAdd,descriptionLoginAdd,spinChoice)
            Log.d("INSERT_RES", res.toString())
            if (res > 0){
                alltasks.add(Task(res.toInt(), idLogUser, dateLoginAdd, titleLoginAdd, descriptionLoginAdd, spinChoice))
                binding.titleAdd.text.clear()
                binding.descriptionAdd.text.clear()
                binding.dateAdd.text.clear()
                Log.d("INSERT", "Inseriu")
                Toast.makeText(this@Add_Task, "Task Created", Toast.LENGTH_LONG).show()
            }
        }

    }
}