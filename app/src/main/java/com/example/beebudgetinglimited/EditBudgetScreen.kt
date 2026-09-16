package com.example.beebudgetinglimited

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditBudgetScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_budget)

        val etMin = findViewById<EditText>(R.id.etMinBudget)
        val etMax = findViewById<EditText>(R.id.etMaxBudget)
        val btnSave = findViewById<Button>(R.id.btnSaveBudget)
        val btnBack = findViewById<ImageButton>(R.id.btnBack)

        // Load existing values from SharedPreferences
        val prefs = getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE)
        etMin.setText(prefs.getInt("minBudget", 1000).toString())
        etMax.setText(prefs.getInt("maxBudget", 1500).toString())

        btnSave.setOnClickListener {
            val minVal = etMin.text.toString().toIntOrNull()
            val maxVal = etMax.text.toString().toIntOrNull()

            if (minVal == null || maxVal == null) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (minVal > maxVal) {
                Toast.makeText(this, "Minimum cannot be greater than Maximum", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save values
            prefs.edit().putInt("minBudget", minVal).putInt("maxBudget", maxVal).apply()
            Toast.makeText(this, "Budget updated successfully!", Toast.LENGTH_SHORT).show()

            finish() // Return to Home screen
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}