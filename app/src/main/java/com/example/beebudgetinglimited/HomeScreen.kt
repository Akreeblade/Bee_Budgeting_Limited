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

        val fullName = intent.getStringExtra("fullName")
        //findViewById<TextView>(R.id.tvWelcome).text = "Welcome, $fullName!"

        // "View all" click listener
        findViewById<TextView>(R.id.tvViewAll).setOnClickListener {
            val intent = Intent(this@HomeScreen, ViewAllHomeScreen::class.java)
            startActivity(intent)
        }

        // Bottom Navigation Bar setup (CORRECTLY OUTSIDE THE VIEW ALL LISTENER)
        findViewById<ImageButton>(R.id.Homebtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Barrybtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, BarryCareScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, ProfileScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, SettingsScreen::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<ImageButton>(R.id.Analyticsbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, Analytics::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<MaterialButton>(R.id.Addbtn).setOnClickListener {
            val intent = Intent(this@HomeScreen, LoggingScreen::class.java)
            startActivity(intent)
            finish()
        }
    } // <-- onCreate ends here!

    override fun onResume() {
        super.onResume()
        // Pulls from repository and restricts to top 3 for the home preview
        val recyclerView = findViewById<RecyclerView>(R.id.rvTransactionHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val recentTransactions = TransactionRepository.getTransactions().take(3)
        recyclerView.adapter = TransactionAdapter(recentTransactions)
    }
}