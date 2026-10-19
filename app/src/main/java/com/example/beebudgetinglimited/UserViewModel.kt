package com.example.beebudgetinglimited

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import com.example.beebudgetinglimited.Appdatabase.AppDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {

    //instance of  firebase
    private val db = FirebaseFirestore.getInstance()
    private val usersCollection = db.collection("users")

    //registers a new user for firestore
    fun registerUser(user: User) = viewModelScope.launch {
        try {
            //username is id for checking if it exists
            usersCollection.document(user.username).set(user).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //checking username and password
    suspend fun loginUser(username: String, password: String): User? = withContext(Dispatchers.IO) {
        try {
            val document = usersCollection.document(username).get().await()
            if (document.exists()) {
                val user = document.toObject(User::class.java)
                // Check if password matches
                if (user != null && user.password == password) {
                    return@withContext user
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }

    //checks if username already exsts
    suspend fun isUsernameTaken(username: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val document = usersCollection.document(username).get().await()
            return@withContext document.exists()
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext false
        }
    }

    /*//instance of userdao from roomdb
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
    }*/
}