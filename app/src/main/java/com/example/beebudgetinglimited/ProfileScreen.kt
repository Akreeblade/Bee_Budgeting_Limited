package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<TextView>(R.id.BackText).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@ProfileScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }

        val streakManager = StreakManager(this)

        // Check and update the streak for today
        val streakResult = streakManager.checkAndUpdateStreak()

        // Find the daily streak TextView (id: textView15 in your XML)
        val tvDailyStreak = findViewById<TextView>(R.id.textView15)

        // Set the text dynamically to show the count
        tvDailyStreak.text = "🔥 Daily Streak: ${streakResult.currentStreak} Days"

        // Optional: Make the streak text clickable to show a quick message or info toast
        tvDailyStreak.setOnClickListener {
            Toast.makeText(
                this,
                "Keep logging daily to grow your streak! Current: ${streakResult.currentStreak} days",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}