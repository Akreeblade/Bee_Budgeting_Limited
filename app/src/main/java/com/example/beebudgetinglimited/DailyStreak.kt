package com.example.beebudgetinglimited

import android.content.Context
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StreakManager(context: Context) {
    private val prefs = context.getSharedPreferences("StreakPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LAST_LOGIN = "last_login_date"
        private const val KEY_CURRENT_STREAK = "current_streak"
    }


    data class StreakResult(val currentStreak: Int, val isStreakUpdated: Boolean)

    fun checkAndUpdateStreak(): StreakResult {
        val today = LocalDate.now()
        val lastLoginStr = prefs.getString(KEY_LAST_LOGIN, null)
        var currentStreak = prefs.getInt(KEY_CURRENT_STREAK, 0)

        if (lastLoginStr == null) {
            // First time user login
            currentStreak = 1
            saveStreakData(today.toString(), currentStreak)
            return StreakResult(currentStreak, true)
        }

        val lastLoginDate = LocalDate.parse(lastLoginStr)
        val daysBetween = ChronoUnit.DAYS.between(lastLoginDate, today)

        return when {
            daysBetween == 0L -> {
                // Already logged in today
                StreakResult(currentStreak, false)
            }
            daysBetween == 1L -> {
                // Logged in consecutively yesterday
                currentStreak += 1
                saveStreakData(today.toString(), currentStreak)
                StreakResult(currentStreak, true)
            }
            else -> {
                // Missed a day or more
                currentStreak = 1
                saveStreakData(today.toString(), currentStreak)
                StreakResult(currentStreak, true)
            }
        }
    }

    private fun saveStreakData(date: String, streak: Int) {
        prefs.edit().apply {
            putString(KEY_LAST_LOGIN, date)
            putInt(KEY_CURRENT_STREAK, streak)
            apply()
        }
    }

    fun getCurrentStreak(): Int {
        return prefs.getInt(KEY_CURRENT_STREAK, 0)
    }
}