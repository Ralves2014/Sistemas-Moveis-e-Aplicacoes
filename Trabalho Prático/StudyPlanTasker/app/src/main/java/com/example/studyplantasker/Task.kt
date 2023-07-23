package com.example.studyplantasker

class Task (val idTask: Int = 0,
                 val idUser: Int = 0,
                 val date: String = "",
                 val nameTask: String = "",
                 val description: String = "",
                 val state: String = ""){
    override fun toString(): String {
        return "Task(idTask=$idTask, idUser=$idUser, date='$date', nameTask='$nameTask', description='$description', state='$state')"
    }
}