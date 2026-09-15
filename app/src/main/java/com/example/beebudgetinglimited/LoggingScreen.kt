package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoggingScreen : AppCompatActivity() {

    private var isIncomeSelected: Boolean = true
    private lateinit var etCategory: EditText

    // Receives category name using Activity Result API
    private val categoryLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedCategory = result.data?.getStringExtra("SELECTED_CATEGORY")
            if (selectedCategory != null) {
                etCategory.setText(selectedCategory)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logging_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        etCategory = findViewById(R.id.etCategory)

        // Set click listener to launch CategoryScreen for selection
        etCategory.setOnClickListener {
            val intent = Intent(this, CategoryScreen::class.java)
            categoryLauncher.launch(intent)
        }

        // Top Navigation Icons
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            startActivity(Intent(this, ProfileScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            startActivity(Intent(this, SettingsScreen::class.java))
            finish()
        }

        // Chip selection tracking
        val cbIncome = findViewById<com.google.android.material.chip.Chip>(R.id.cbIncome)
        val cbExpense = findViewById<com.google.android.material.chip.Chip>(R.id.cbExpense)

        // Default selection setup
        cbIncome.isChecked = true

        cbIncome.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) isIncomeSelected = true
        }
        cbExpense.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) isIncomeSelected = false
        }

        // Optional Camera Button -> Opens Device Camera
        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)
        btnCamera?.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(cameraIntent)
        }

        // Save Button -> Validates and saves transaction
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etNotes = findViewById<EditText>(R.id.etNotes)

        val amountStr = etAmount?.text.toString().trim()
        val categoryStr = etCategory.text.toString().trim()
        val notesStr = etNotes?.text.toString().trim()

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return
        }

        val amountValue = amountStr.toDoubleOrNull()
        if (amountValue == null || amountValue <= 0.0) {
            Toast.makeText(this, "Please enter a valid number amount", Toast.LENGTH_SHORT).show()
            return
        }

        if (categoryStr.isEmpty()) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }

        // Format the amount string (e.g., "-R250" for expense or "R1200" for income)
        val formattedAmount = if (isIncomeSelected) {
            "R${amountValue.toInt()}"
        } else {
            "-R${amountValue.toInt()}"
        }

        // Create the transaction object
        val newTransaction = TransactionItem(
            title = categoryStr,
            amount = formattedAmount,
            note = if (notesStr.isEmpty()) null else notesStr,
            date = "12 August 2026",
            isIncome = isIncomeSelected
        )

        // Save it to the repository
        TransactionRepository.addTransaction(newTransaction)

        Toast.makeText(this, "Transaction Saved Successfully!", Toast.LENGTH_SHORT).show()

        // Head back to Home Screen
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }
}