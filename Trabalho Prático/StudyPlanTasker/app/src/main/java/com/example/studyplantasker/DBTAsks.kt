package com.example.studyplantasker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.app.INotificationSideChannel

class DBTAsks(context: Context) :  SQLiteOpenHelper(context, "task.db", null, 1){

    val sql = arrayOf(
        "CREATE TABLE tasks (idTask INTEGER PRIMARY KEY AUTOINCREMENT, idUser INTEGER, date TEXT, description TEXT, nameTask TEXT, state TEXT)",
        "INSERT INTO tasks (idUser, date, description, nameTask, state) VALUES (3, '30/06/2023', 'Trabalho de IA', 'Prolog Work', 'TO DO')"
    )

    override fun onCreate(dbt: SQLiteDatabase) {
        sql.forEach {
            dbt.execSQL(it)
        }
    }

    override fun onUpgrade(dbt: SQLiteDatabase, p1: Int, p2: Int) {
        dbt.execSQL("DROP TABLE tasks")
        onCreate(dbt)
    }

    fun taskInsert(idUser: Int, date: String, description: String, nameTask: String, state: String) : Long{
        val dbt = this.writableDatabase
        val values = ContentValues()
        values.put("idUser", idUser)
        values.put("date", date)
        values.put("description", description)
        values.put("nameTask", nameTask)
        values.put("state", state)
        val res = dbt.insert("tasks", null, values)
        dbt.close()
        return res
    }

    fun tasksUpdate(idTask: Int, idUser: Int, date: String, description: String, nameTask: String, state: String): Int {
        val dbt = this.writableDatabase
        val values = ContentValues()
        values.put("idUser", idUser)
        values.put("date", date)
        values.put("nameTask", nameTask)
        values.put("description", description)
        values.put("state", state)
        val res = dbt.update("tasks", values, "idTask=?", arrayOf(idTask.toString()))
        dbt.close()
        return res
    }

    fun taskDelete(idTask: Int) : Int{
        val dbt = this.writableDatabase
        val res = dbt.delete("tasks","idTask=?", arrayOf(idTask.toString()))
        dbt.close()
        return res
    }

    fun taskselectAll(): Cursor {
        val dbt = this.readableDatabase
        val result = dbt.rawQuery("SELECT * FROM tasks", null)
        dbt.close()
        return result
    }

    fun taskselectById(idTask : Int): Cursor{
        val dbt = this.readableDatabase
        val result = dbt.rawQuery("SELECT * FROM task WHERE idTask=?", arrayOf(idTask.toString()))
        dbt.close()
        return result
    }

    fun selectByIdTask(idTask : Int) : Task{
        val dbt = this.readableDatabase
        val result = dbt.rawQuery("SELECT * FROM tasks WHERE idTask=?", arrayOf(idTask.toString()))
        var task = Task()

        if (result.count == 1){
            result.moveToFirst()

            val idTaskIndex = result.getColumnIndex("idTask")
            val idUserIndex = result.getColumnIndex("idUser")
            val dateIndex = result.getColumnIndex("date")
            val descriptionIndex = result.getColumnIndex("description")
            val nameTaskIndex = result.getColumnIndex("nameTask")
            val stateIndex = result.getColumnIndex("state")

            val idTask = result.getInt(idTaskIndex)
            val idUser = result.getInt(idUserIndex)
            val date = result.getString(dateIndex)
            val description = result.getString(descriptionIndex)
            val nameTask = result.getString(nameTaskIndex)
            val state = result.getString(stateIndex)


            task = Task(idTask, idUser, date, description, nameTask, state)
        }
        dbt.close()
        return task
    }

    fun selectallTasks() : ArrayList<Task>{
        val dbt = this.readableDatabase
        val result = dbt.rawQuery("SELECT * FROM tasks", null)
        val resultList : ArrayList<Task> = ArrayList()

        if(result.count > 0){
            result.moveToFirst()
            do {
                val idTaskIndex = result.getColumnIndex("idTask")
                val idUserIndex = result.getColumnIndex("idUser")
                val dateIndex = result.getColumnIndex("date")
                val descriptionIndex = result.getColumnIndex("description")
                val nameTaskIndex = result.getColumnIndex("nameTask")
                val stateIndex = result.getColumnIndex("state")

                val idTask = result.getInt(idTaskIndex)
                val idUser = result.getInt(idUserIndex)
                val date = result.getString(dateIndex)
                val description = result.getString(descriptionIndex)
                val nameTask = result.getString(nameTaskIndex)
                val state = result.getString(stateIndex)

                resultList.add(Task(idTask, idUser, date, description, nameTask, state))

            }while (result.moveToNext())
        }
        dbt.close()

        return resultList
    }
    fun selectallByState(state:String, idLogUser: Int): ArrayList<Task>{
        val dbt = this.readableDatabase
        val result = dbt.rawQuery("SELECT * FROM tasks WHERE state=? AND idUser=?", arrayOf(state.toString(), idLogUser.toString()))
        val resultList : ArrayList<Task> = ArrayList()

        if(result.count > 0){
            result.moveToFirst()
            do {
                val idTaskIndex = result.getColumnIndex("idTask")
                val idUserIndex = result.getColumnIndex("idUser")
                val dateIndex = result.getColumnIndex("date")
                val descriptionIndex = result.getColumnIndex("description")
                val nameTaskIndex = result.getColumnIndex("nameTask")
                val stateIndex = result.getColumnIndex("state")

                val idTask = result.getInt(idTaskIndex)
                val idUser = result.getInt(idUserIndex)
                val date = result.getString(dateIndex)
                val description = result.getString(descriptionIndex)
                val nameTask = result.getString(nameTaskIndex)
                val state = result.getString(stateIndex)

                resultList.add(Task(idTask, idUser, date, description, nameTask, state))

            }while (result.moveToNext())
        }
        dbt.close()

        return resultList
    }
}