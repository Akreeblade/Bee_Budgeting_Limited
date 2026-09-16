package com.example.beebudgetinglimited

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ViewAllHomeScreen : AppCompatActivity() {

    private lateinit var etStartDate: EditText
    private lateinit var etEndDate: EditText
    private lateinit var cbGroupByCategory: CheckBox
    private lateinit var recyclerView: RecyclerView
    private val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)

    private var startDateSelected: Date? = null
    private var endDateSelected: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_all_home_screen)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etStartDate = findViewById(R.id.etStartDate)
        etEndDate = findViewById(R.id.etEndDate)
        cbGroupByCategory = findViewById(R.id.cbGroupByCategory)
        recyclerView = findViewById(R.id.rvAllTransactions)

        etStartDate.setOnClickListener { showDatePicker { date, formatted ->
            startDateSelected = date
            etStartDate.setText(formatted)
        }}

        etEndDate.setOnClickListener { showDatePicker { date, formatted ->
            endDateSelected = date
            etEndDate.setText(formatted)
        }}

        findViewById<Button>(R.id.btnFilter).setOnClickListener {
            applyFilterAndGrouping()
        }

        findViewById<Button>(R.id.btnClearFilter).setOnClickListener {
            startDateSelected = null
            endDateSelected = null
            etStartDate.setText("")
            etEndDate.setText("")
            cbGroupByCategory.isChecked = false
            loadAllTransactions()
        }

        cbGroupByCategory.setOnCheckedChangeListener { _, _ ->
            applyFilterAndGrouping()
        }

        // Navigation bindings
        findViewById<TextView>(R.id.tvBack)?.setOnClickListener {
            startActivity(Intent(this, HomeScreen::class.java))
            finish()
        }
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
        applyFilterAndGrouping()
    }

    private fun loadAllTransactions() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(TransactionRepository.getTransactions())
    }

    private fun showDatePicker(onDateSelected: (Date, String) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedCal = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay, 0, 0, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val formattedDate = dateFormat.format(selectedCal.time)
                onDateSelected(selectedCal.time, formattedDate)
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun applyFilterAndGrouping() {
        var baseList = TransactionRepository.getTransactions()

        // Filter by Date Range if specified
        if (startDateSelected != null && endDateSelected != null) {
            if (startDateSelected!!.after(endDateSelected)) {
                Toast.makeText(this, "Start date cannot be after end date", Toast.LENGTH_SHORT).show()
                return
            }

            baseList = baseList.filter { item ->
                try {
                    val itemDate = dateFormat.parse(item.date)
                    itemDate != null && !itemDate.before(startDateSelected) && !itemDate.after(endDateSelected)
                } catch (e: Exception) {
                    false
                }
            }
        }

        // Group by Category if CheckBox is active
        val finalDisplayList = if (cbGroupByCategory.isChecked) {
            val groupedMap = mutableMapOf<String, Pair<Double, Boolean>>()

            for (item in baseList) {
                val cleanAmt = item.amount.replace("R", "").replace("-", "").trim().toDoubleOrNull() ?: 0.0
                val existing = groupedMap[item.title]
                val newTotal = (existing?.first ?: 0.0) + cleanAmt
                groupedMap[item.title] = Pair(newTotal, item.isIncome)
            }

            groupedMap.map { (category, pair) ->
                val prefix = if (pair.second) "R" else "-R"
                TransactionItem(
                    title = category,
                    date = "Category Total",
                    amount = String.format(Locale.ENGLISH, "%s%.2f", prefix, pair.first),
                    isIncome = pair.second,
                    note = "Total sum for selected period"
                )
            }
        } else {
            baseList
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TransactionAdapter(finalDisplayList)

        if (finalDisplayList.isEmpty() && (startDateSelected != null || endDateSelected != null)) {
            Toast.makeText(this, "No transactions found", Toast.LENGTH_SHORT).show()
        }
    }
}