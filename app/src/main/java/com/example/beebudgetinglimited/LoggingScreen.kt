package com.example.beebudgetinglimited

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import java.io.File
import java.util.Calendar

class LoggingScreen : AppCompatActivity() {
    private var photoUri: Uri? = null

    private val cameraLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            Toast.makeText(this, "Photo saved!", Toast.LENGTH_SHORT).show()
        } else {
            photoUri = null
            Toast.makeText(this, "Photo cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private var isIncomeSelected: Boolean = true
    private lateinit var etCategory: EditText
    private lateinit var etDate: EditText

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
        etDate = findViewById(R.id.etDate)

        // Date Picker Handler
        etDate.setOnClickListener {
            showDatePicker()
        }

        // Launch CategoryScreen with filter flag
        etCategory.setOnClickListener {
            val intent = Intent(this, CategoryScreen::class.java).apply {
                putExtra("IS_INCOME", isIncomeSelected)
            }
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
        val cbIncome = findViewById<Chip>(R.id.cbIncome)
        val cbExpense = findViewById<Chip>(R.id.cbExpense)

        // Default selection setup
        cbIncome.isChecked = true

        cbIncome.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (!isIncomeSelected) {
                    isIncomeSelected = true
                    etCategory.setText("") // Reset incompatible selection
                }
            }
        }
        cbExpense.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isIncomeSelected) {
                    isIncomeSelected = false
                    etCategory.setText("") // Reset incompatible selection
                }
            }
        }

        // Optional Camera Button -> Opens Device Camera
        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)
        btnCamera?.setOnClickListener {
            val uri = createPhotoUri()
            photoUri = uri
            cameraLauncher.launch(uri)
        }

        // Save Button -> Validates and saves transaction
        val btnSave = findViewById<Button>(R.id.btnSave)
        btnSave.setOnClickListener {
            saveTransaction()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val monthNames = arrayOf(
                    "January", "February", "March", "April", "May", "June",
                    "July", "August", "September", "October", "November", "December"
                )
                val formattedDate = "$selectedDay ${monthNames[selectedMonth]} $selectedYear"
                etDate.setText(formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()  //so they cant choose a future date
        datePickerDialog.show()
    }

    private fun saveTransaction() {
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val etNotes = findViewById<EditText>(R.id.etNotes)

        val amountStr = etAmount?.text.toString().trim()
        val categoryStr = etCategory.text.toString().trim()
        val dateStr = etDate.text.toString().trim()
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

        if (dateStr.isEmpty()) {
            Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show()
            return
        }

        // Format amount string
        val formattedAmount = if (isIncomeSelected) {
            "R${amountValue.toInt()}"
        } else {
            "-R${amountValue.toInt()}"
        }

        // Create transaction object
        val newTransaction = TransactionItem(
            title = categoryStr,
            amount = formattedAmount,
            note = if (notesStr.isEmpty()) null else notesStr,
            date = dateStr,
            isIncome = isIncomeSelected,
            photoUri = photoUri?.toString()
        )

        // Save to repository
        TransactionRepository.addTransaction(newTransaction)

        Toast.makeText(this, "Transaction Saved Successfully!", Toast.LENGTH_SHORT).show()

        // Return to Home Screen
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun createPhotoUri(): Uri {
        val photoFile = File.createTempFile(
            "transaction_photo_",
            ".jpg",
            cacheDir
        )

        return FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.fileprovider",
            photoFile
        )
    }
}