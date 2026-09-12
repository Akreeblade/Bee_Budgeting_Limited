package com.example.palworld

import com.example.beebudgetinglimited.User

class UserviewModel {
    private val UserDao = Appdatabase.getDatabase(application).UserDao

    fun registerUser(user:User)= viewModelScope.Lauch{
        UserDao.insertUser(user)
    }

    suspend fun  loginUser(username: String, password: String ): User? {
        return UserDao.login(username,password)

    }

    suspend fun isUsernameTaken(username: String): Boolean{
        return UserDao.getUserByUsername(username) != null
    }
}