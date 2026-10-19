package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class AddCategoryScreen : AppCompatActivity() {

    private var isIncomeSelected: Boolean = false // Default to Expense

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_category_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etCategoryName = findViewById<TextInputEditText>(R.id.etCategoryName) // Add android:id="@+id/etCategoryName" to your XML TextInputEditText
        val chipIncome = findViewById<Chip>(R.id.chip3)
        val chipExpense = findViewById<Chip>(R.id.chip4)
        val btnSave = findViewById<Button>(R.id.btnSave)

        // Setup Selection Handling
        chipIncome.setOnClickListener {
            isIncomeSelected = true
        }

        chipExpense.setOnClickListener {
            isIncomeSelected = false
        }

        // Save Logic
        btnSave.setOnClickListener {
            val name = etCategoryName?.text?.toString()?.trim() ?: ""

            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a category name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val db = FirebaseFirestore.getInstance()

            // Create category map or data object
            val categoryData = hashMapOf(
                "name" to name,
                "isIncome" to isIncomeSelected
            )

            // Save to Firestore "categories" collection
            db.collection("categories")
                .add(categoryData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
                    finish() // Closes screen and returns to CategoryScreen
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save category: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

            /*// Save to repository
            val newCategory = Category(name = name, isIncome = isIncomeSelected)
            CategoryRepository.addCategory(newCategory)

            Toast.makeText(this, "Category saved successfully", Toast.LENGTH_SHORT).show()
            finish() // Closes screen and updates CategoryScreen*/
        }

        // Navigation
        findViewById<ImageButton>(R.id.ProfileBtn).setOnClickListener {
            startActivity(Intent(this, ProfileScreen::class.java))
            finish()
        }
        findViewById<ImageButton>(R.id.Settingsbtn).setOnClickListener {
            startActivity(Intent(this, SettingsScreen::class.java))
            finish()
        }
    }
}