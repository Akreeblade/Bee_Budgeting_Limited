package com.example.beebudgetinglimited

import android.content.Intent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ViewAllHomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_all_home_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<Button>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }
    }
}