package com.example.beebudgetinglimited

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.beebudgetinglimited.Appdatabase.AppDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    //instance of userdao from roomdb
    private val userDao = AppDatabase.getDatabase(application).userDao()

    //registers a new user
    fun registerUser(user: User) = viewModelScope.launch {
        userDao.insertUser(user)
    }

    //checks if user exists
    suspend fun loginUser(username: String, password: String): User? {
        return userDao.login(username, password)
    }

    //checks if username exists
    suspend fun isUsernameTaken(username: String): Boolean {
        return userDao.getUserByUsername(username) != null
    }
}