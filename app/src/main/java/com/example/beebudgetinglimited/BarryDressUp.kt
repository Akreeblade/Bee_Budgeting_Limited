package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BarryDressUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_barry_dress_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Settingbtnprf).setOnClickListener {
            val intent = Intent(this@BarryDressUp, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@BarryDressUp, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }

    }
}