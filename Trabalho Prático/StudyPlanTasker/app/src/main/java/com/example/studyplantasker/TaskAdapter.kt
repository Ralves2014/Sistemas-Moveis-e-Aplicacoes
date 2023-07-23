package com.example.studyplantasker

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.Date

class TaskAdapter (private val taskList: ArrayList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>(){
    private lateinit var title1: String
    private lateinit var date1: String
    private lateinit var remaningTime1: String
    private lateinit var pos: String
    private var count: Int = 0

    class TaskViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.tv_title)
        val date: TextView = view.findViewById(R.id.tv_date)
        val remaningT: TextView = view.findViewById(R.id.tv_remaningTime)
        val ImageWarning: ImageView= view.findViewById(R.id.warningImg)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activiy_task, parent, false)
        val cardView: CardView = view.findViewById(R.id.cv_card)
        val viewHolder = TaskViewHolder(view)

        viewHolder.itemView.setOnClickListener {
            // Handle the click event
            val idt = view.findViewById<TextView>(R.id.tv_title).text
            val position = getItemPosition(idt.toString())

            Log.d("NAMETASK", idt.toString())
            Log.d("POSITION", position.toString())
            if (position != RecyclerView.NO_POSITION) {
                if (position != null) {
                    if (position < taskList.size) {
                        val item = taskList[position]
                        Log.d("IDTASK", item.idTask.toString())
                        // Start the new activity
                        val intent = Intent(parent.context, TaskInfo::class.java)
                        intent.putExtra(
                            "itemId",
                            item.idTask.toString()
                        ) // Pass any necessary data to the new activity
                        parent.context.startActivity(intent)
                    } else {
                        // Handle the case where the item is no longer in the dataset
                        // Log an error or show a message if needed

                    }
                }
            }
        }
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        title1 = taskList[position].nameTask
        date1 = taskList[position].date
        holder.title.text = taskList[position].nameTask
        holder.date.text = taskList[position].date
        val date = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val finalCurrentDate: String =formatter.format(date)

        val diference = decodeDate(finalCurrentDate, date1)

        if (diference.toInt() >= 0){
            holder.remaningT.setTextColor(Color.GREEN)
            if(diference.toInt() > 5){
                holder.ImageWarning.visibility = View.INVISIBLE
            }
            else{
                count = count +1
            }
        }
        else{
            holder.remaningT.setTextColor(Color.RED)
        }

        Log.d("DATE NOW", finalCurrentDate)
        Log.d("DIFERENCE", diference)

        val toputintextdate = diference + " days"
        if(taskList[position].state != "DONE"){
            holder.remaningT.text = toputintextdate
        }
        else{
            holder.ImageWarning.visibility = View.INVISIBLE
        }
    }
    fun getArrayList(): ArrayList<String>{
        val nameList = ArrayList<String>()
        taskList.forEach {
            nameList.add(it.nameTask)
        }
        return nameList
    }
    fun getItemPosition(item: String): Int {
        val nameList = getArrayList()
        return nameList.indexOf(item)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun decodeDate(currentD: String, dataDate: String): String{

        var decodeCurrentdate = currentD.split("/")
        var decodeDatadate = dataDate.split("/")

        val date1 = LocalDate.of(decodeCurrentdate[2].toInt(), decodeCurrentdate[1].toInt(), decodeCurrentdate[0].toInt())
        val date2 = LocalDate.of(decodeDatadate[2].toInt(), decodeDatadate[1].toInt(), decodeDatadate[0].toInt())

        val difference = ChronoUnit.DAYS.between(date1, date2)

        val finalTimedates = difference.toString()

        return finalTimedates
    }
}