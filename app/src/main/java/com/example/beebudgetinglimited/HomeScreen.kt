package com.example.beebudgetinglimited

import android.content.Context
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
import com.google.android.material.progressindicator.LinearProgressIndicator

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

        // "View all" click listener
        findViewById<TextView>(R.id.tvViewAll).setOnClickListener {
            val intent = Intent(this@HomeScreen, ViewAllHomeScreen::class.java)
            startActivity(intent)
        }

        // Make Monthly Budget section clickable to open Edit Budget Screen
        findViewById<com.google.android.material.progressindicator.LinearProgressIndicator>(R.id.progressBarBudget).setOnClickListener {
            startActivity(Intent(this, EditBudgetScreen::class.java))
        }
        findViewById<TextView>(R.id.tvBudgetTitle).setOnClickListener {
            startActivity(Intent(this, EditBudgetScreen::class.java))
        }
        findViewById<TextView>(R.id.tvBudgetAmounts).setOnClickListener {
            startActivity(Intent(this, EditBudgetScreen::class.java))
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
    }

    override fun onResume() {
        super.onResume()

        // 1. Load recent transaction preview (top 3)
        val recyclerView = findViewById<RecyclerView>(R.id.rvTransactionHistory)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val allTransactions = TransactionRepository.getTransactions()
        val recentTransactions = allTransactions.take(3)
        recyclerView.adapter = TransactionAdapter(recentTransactions)

        // 2. Calculate dynamic totals for Income, Expenses, and Balance
        var totalIncome = 0
        var totalExpenses = 0

        for (item in allTransactions) {
            val numericValue = item.amount.replace(Regex("[^0-9]"), "").toIntOrNull() ?: 0
            if (item.isIncome) {
                totalIncome += numericValue
            } else {
                totalExpenses += numericValue
            }
        }

        val balance = totalIncome - totalExpenses

        // 3. Update Overview Card TextViews
        findViewById<TextView>(R.id.tvIncomeTotal).text = "R$totalIncome"
        findViewById<TextView>(R.id.tvExpensesTotal).text = "R$totalExpenses"
        findViewById<TextView>(R.id.tvBalanceTotal).text = "R$balance"

        // 4. Load Custom Min and Max Budgets from SharedPreferences
        val prefs = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
        val minBudget = prefs.getInt("minBudget", 1000)
        val maxBudget = prefs.getInt("maxBudget", 1500)

        // Update Text to show Expenses vs Max Limit (or Min/Max details)
        findViewById<TextView>(R.id.tvBudgetAmounts).text = "R$totalExpenses / R$maxBudget"

        // Calculate progress relative to the maximum budget cap
        val progressPercent = if (maxBudget > 0) {
            ((totalExpenses.toFloat() / maxBudget) * 100).toInt().coerceIn(0, 100)
        } else {
            0
        }

        val progressBar = findViewById<LinearProgressIndicator>(R.id.progressBarBudget)
        progressBar.progress = progressPercent

        // Change progress bar color based on whether expenses passed the minimum target or max limit
        when {
            totalExpenses > maxBudget -> {
                progressBar.setIndicatorColor(android.graphics.Color.parseColor("#FF3B30")) // Red for over max
            }
            totalExpenses >= minBudget -> {
                progressBar.setIndicatorColor(android.graphics.Color.parseColor("#FF9638")) // Orange for hitting min goal zone
            }
            else -> {
                progressBar.setIndicatorColor(android.graphics.Color.parseColor("#FFB817")) // Yellow for safe zone below min
            }
        }
    }
}