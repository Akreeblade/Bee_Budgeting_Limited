package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BarryCareScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_barry_care_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Settingbtnprf).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@BarryCareScreen, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}