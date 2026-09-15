package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

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

        // Top-left "Back" text button to return to Home Screen
        findViewById<TextView>(R.id.tvBack)?.setOnClickListener {
            val intent = Intent(this@ViewAllHomeScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }

        // Bottom Navigation Bar setup
        findViewById<ImageButton>(R.id.Homebtn).setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.Barrybtn).setOnClickListener {
            startActivity(Intent(this, BarryCareScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            startActivity(Intent(this, ProfileScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            startActivity(Intent(this, SettingsScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.Analyticsbtn).setOnClickListener {
            startActivity(Intent(this, Analytics::class.java))
            finish()
        }
        findViewById<MaterialButton>(R.id.Addbtn).setOnClickListener {
            startActivity(Intent(this, LoggingScreen::class.java))
            finish()
        }
    } // <-- onCreate ends here!

    override fun onResume() {
        super.onResume()
        // Pulls the complete list from your repository for full scrolling history
        val recyclerView = findViewById<RecyclerView>(R.id.rvAllTransactions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(TransactionRepository.getTransactions())
    }
}