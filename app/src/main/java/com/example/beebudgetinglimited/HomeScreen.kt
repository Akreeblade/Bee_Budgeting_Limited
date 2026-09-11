package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}