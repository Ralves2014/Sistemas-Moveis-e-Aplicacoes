package com.example.studyplantasker

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBLogin(context : Context) : SQLiteOpenHelper(context, "login.db", null, 1) {

    val sql = arrayOf(
        "CREATE TABLE utilizador (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)",
        "INSERT INTO utilizador (username, password) VALUES ('admin', 'pwd')",
        "INSERT INTO utilizador (username, password) VALUES ('user', 'pass')"
    )


    override fun onCreate(db: SQLiteDatabase) {
        sql.forEach {
            db.execSQL(it)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE utilizador")
        onCreate(db)
    }

    fun userInsert(username : String, password : String) : Long{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("password", password)
        val res = db.insert("utilizador", null, values)
        db.close()
        return res
    }
    fun userDelete(name: String) : Int{
        val db = this.writableDatabase
        val res = db.delete("utilizador","username=?", arrayOf(name))
        db.close()
        return res
    }

    fun userselectAll(): Cursor{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM utilizador", null)
        db.close()
        return result
    }

    fun userselectById(id : Int): Cursor{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM utilizador WHERE id=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun selectById(id : Int) : Utilizador{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM utilizador WHERE id=?", arrayOf(id.toString()))
        var user = Utilizador()

        if (result.count == 1){
            result.moveToFirst()

            val idIndex = result.getColumnIndex("id")
            val userIndex = result.getColumnIndex("username")
            val passIndex = result.getColumnIndex("password")

            val id = result.getInt(idIndex)
            val username = result.getString(userIndex)
            val password = result.getString(passIndex)

            user = Utilizador(id, username, password)
        }
        db.close()
        return user
    }
    fun selectByName(name: String) : Int{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM utilizador WHERE username=?", arrayOf(name))
        var finalID = 0
        Log.d("RESULTCOUNT", result.count.toString())
        if (result.count == 1){
            result.moveToFirst()
            Log.d("ENTROU", "ENTROU")
            val idIndex = result.getColumnIndex("id")
            val id = result.getInt(idIndex)
            Log.d("FINALID", id.toString())
            finalID = id
        }
        db.close()
        return  finalID
    }

    fun selectall() : ArrayList<Utilizador>{
        val db = this.readableDatabase
        val result = db.rawQuery("SELECT * FROM utilizador", null)
        val resultList : ArrayList<Utilizador> = ArrayList()

        if(result.count > 0){
            result.moveToFirst()
            do {
                val idIndex = result.getColumnIndex("id")
                val userIndex = result.getColumnIndex("username")
                val passIndex = result.getColumnIndex("password")

                val id = result.getInt(idIndex)
                val username = result.getString(userIndex)
                val password = result.getString(passIndex)

                resultList.add(Utilizador(id, username, password))

            }while (result.moveToNext())
        }
        db.close()

        return resultList
    }
}