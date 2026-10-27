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

        // checks the streak then updates the streak
        val streakResult = streakManager.checkAndUpdateStreak()

        // finds the daily streak text view
        val tvDailyStreak = findViewById<TextView>(R.id.textView15)

        // dynamic seeting of text (yoo fire emoji)
        tvDailyStreak.text = "🔥 Daily Streak: ${streakResult.currentStreak} Days"

        // makes the streak clickable to display a message
        tvDailyStreak.setOnClickListener {
            Toast.makeText(
                this,
                "Keep logging daily to grow your streak! Current: ${streakResult.currentStreak} days",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}