package com.example.studyplantasker

class Utilizador (val id : Int = 0, val username : String =  "", val password : String = "") {

    override fun toString(): String {
        return "User(id=$id, username='$username', password='$password')"
    }
}