package com.example.beebudgetinglimited

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

class Appdatabase {

    /*
    Android Developers, s.a. Save data in a local database using Room (Version n/a) [Source code]
Available at:
<https://developer.android.com/training/data-storage/room>
[Accessed 13 September 2026].

     */
    @Database(entities = [User::class], version = 1, exportSchema = false)

//abstract class because it extends RoomDatabase
    abstract class AppDatabase : RoomDatabase() {
        /*
        must define an abstract method with no arguments that returns
        DAO class. Must be created for each DAO class
         */

        abstract fun userDao(): UserDao

        companion object {
            @Volatile private var INSTANCE: AppDatabase? = null

            //creates instance of database
            fun getDatabase(context: Context): AppDatabase {
                return INSTANCE ?: synchronized(this) {
                    Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "user_db"
                    )
                        //.fallbackToDestructiveMigration()
                        .build().also {INSTANCE = it}
                }
            }
        }
    }}