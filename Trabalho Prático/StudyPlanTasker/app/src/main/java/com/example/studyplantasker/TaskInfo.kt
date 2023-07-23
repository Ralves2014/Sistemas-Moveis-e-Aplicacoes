package com.example.studyplantasker

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import com.example.studyplantasker.databinding.ActivityAddTaskBinding
import com.example.studyplantasker.databinding.ActivityInfoTaskBinding

class TaskInfo: AppCompatActivity() {

    private lateinit var binding: ActivityInfoTaskBinding
    private lateinit var username: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_info_task)

        val idTASK = intent.getStringExtra("itemId")?.toInt()
        Log.d("IDTASKCORRECT", idTASK.toString())

        val dbT = DBTAsks(this)

        var task = idTASK?.let { dbT.selectByIdTask(it) }

        var TASK_NAME: String = ""
        var TASK_DATA: String = ""
        var TASK_DESCRIPTION: String = ""
        var TASK_STATE: String = ""

        binding = ActivityInfoTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (task != null) {
            binding.titleAdd.setText(task.nameTask)
            binding.dateAdd.setText(task.date)
            binding.descriptionAdd.setText(task.description)
            binding.currentState.text = task.state

            TASK_DATA = task.date
            TASK_NAME = task.nameTask
            TASK_DESCRIPTION = task.description
            TASK_STATE = task.state

            val dbU = DBLogin(this)

            username = dbU.selectById(task.idUser).username
        }

        binding.btTODO.setOnClickListener {
            TASK_STATE = "TO DO"
            binding.currentState.text = TASK_STATE
        }
        binding.btDOING.setOnClickListener {
            TASK_STATE = "DOING"
            binding.currentState.text = TASK_STATE
        }
        binding.btDONE.setOnClickListener {
            TASK_STATE = "DONE"
            binding.currentState.text = TASK_STATE
        }

        binding.seetasks.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            intent2.putExtra("message_key", username)
            startActivity(intent2)
        }

        binding.changetask.setOnClickListener {
            if (task != null){

                TASK_NAME = binding.titleAdd.text.toString()
                TASK_DATA = binding.dateAdd.text.toString()
                TASK_DESCRIPTION = binding.descriptionAdd.text.toString()


                val res = dbT.tasksUpdate(task.idTask, task.idUser, TASK_DATA, TASK_NAME, TASK_DESCRIPTION, TASK_STATE)

                if (res > 0){
                    Toast.makeText(this@TaskInfo, "Task UPDATED", Toast.LENGTH_LONG).show()
                    val intent6 = Intent(this, MainActivity::class.java)
                    intent6.putExtra("message_key", username)
                    startActivity(intent6)
                }
            }
            val intent6 = Intent(this, MainActivity::class.java)
            intent6.putExtra("message_key", username)
            startActivity(intent6)
        }


        binding.deletetask.setOnClickListener {
            if (task != null) {
                dbT.taskDelete(task.idTask)
                Toast.makeText(this@TaskInfo, "Task Deleted", Toast.LENGTH_LONG).show()
            }
            val intent7 = Intent(this, MainActivity::class.java)
            intent7.putExtra("message_key", username)
            startActivity(intent7)
        }




    }
}