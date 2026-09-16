package com.example.beebudgetinglimited

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class Analytics : AppCompatActivity() {

    private lateinit var pieChart: PieChart
    private lateinit var tvDateRange: TextView
    private lateinit var tvChartTitle: TextView
    private lateinit var legendContainer: LinearLayout

    private var selectedCalendar: Calendar = Calendar.getInstance()
    private var isIncomeView: Boolean = false // Default to Expense pie chart view

    // High-contrast color palette matching wireframe theme
    private val chartColors = listOf(
        Color.parseColor("#C8A600"), // Yellow-Green
        Color.parseColor("#D0732A"), // Warm Orange
        Color.parseColor("#5A9054"), // Forest Green
        Color.parseColor("#4A6B82"), // Muted Blue
        Color.parseColor("#8E528D")  // Soft Purple
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analytics)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pieChart = findViewById(R.id.pieChart)
        tvDateRange = findViewById(R.id.tvDateRange)
        tvChartTitle = findViewById(R.id.tvChartTitle)
        legendContainer = findViewById(R.id.legendContainer)

        val chipExpenses = findViewById<Chip>(R.id.chipExpenses)
        val chipIncome = findViewById<Chip>(R.id.chipIncome)

        chipExpenses.isChecked = true

        chipExpenses.setOnClickListener {
            isIncomeView = false
            updateAnalyticsView()
        }

        chipIncome.setOnClickListener {
            isIncomeView = true
            updateAnalyticsView()
        }

        // Date selection listener to switch target month
        tvDateRange.setOnClickListener {
            showMonthYearPicker()
        }

        // Setup bottom menu navigation handlers
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

        updateAnalyticsView()
    }

    override fun onResume() {
        super.onResume()
        updateAnalyticsView()
    }

    private fun showMonthYearPicker() {
        val year = selectedCalendar.get(Calendar.YEAR)
        val month = selectedCalendar.get(Calendar.MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, _ ->
                selectedCalendar.set(Calendar.YEAR, selectedYear)
                selectedCalendar.set(Calendar.MONTH, selectedMonth)
                updateAnalyticsView()
            },
            year,
            month,
            1
        )
        datePickerDialog.show()
    }

    private fun updateAnalyticsView() {
        val monthFormat = SimpleDateFormat("1 MMMM - 31 MMMM yyyy", Locale.ENGLISH)
        tvDateRange.text = monthFormat.format(selectedCalendar.time)
        tvChartTitle.text = if (isIncomeView) "Total Income" else "Total Expenses"

        val currentMonth = selectedCalendar.get(Calendar.MONTH)
        val currentYear = selectedCalendar.get(Calendar.YEAR)
        val dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.ENGLISH)

        // Retrieve recorded entries matching month and selected type
        val matchingTransactions = TransactionRepository.getTransactions().filter { item ->
            try {
                val parsedDate = dateFormat.parse(item.date)
                if (parsedDate != null) {
                    val cal = Calendar.getInstance().apply { time = parsedDate }
                    cal.get(Calendar.MONTH) == currentMonth &&
                            cal.get(Calendar.YEAR) == currentYear &&
                            item.isIncome == isIncomeView
                } else false
            } catch (e: Exception) {
                false
            }
        }

        // Calculate total sum per category
        val categoryTotals = mutableMapOf<String, Double>()
        var grandTotal = 0.0

        for (item in matchingTransactions) {
            val cleanAmount = item.amount.replace("R", "").replace("-", "").trim().toDoubleOrNull() ?: 0.0
            categoryTotals[item.title] = (categoryTotals[item.title] ?: 0.0) + cleanAmount
            grandTotal += cleanAmount
        }

        renderPieChart(categoryTotals, grandTotal)
        renderCategoryBars(categoryTotals, grandTotal)
    }

    private fun renderPieChart(categoryTotals: Map<String, Double>, grandTotal: Double) {
        if (grandTotal == 0.0) {
            pieChart.clear()
            pieChart.setNoDataText("No transactions logged for this month")
            pieChart.setNoDataTextColor(Color.parseColor("#725030"))
            return
        }

        val entries = mutableListOf<PieEntry>()
        categoryTotals.forEach { (category, amount) ->
            val percentage = (amount / grandTotal) * 100
            entries.add(PieEntry(percentage.toFloat(), category))
        }

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = chartColors
        dataSet.setDrawValues(false)

        val data = PieData(dataSet)
        pieChart.data = data
        pieChart.description.isEnabled = false
        pieChart.legend.isEnabled = false
        pieChart.isDrawHoleEnabled = false // Solid pie chart matching wireframe
        pieChart.animateY(800)
        pieChart.invalidate()
    }

    private fun renderCategoryBars(categoryTotals: Map<String, Double>, grandTotal: Double) {
        legendContainer.removeAllViews()

        if (grandTotal == 0.0) return

        var colorIndex = 0
        categoryTotals.forEach { (category, amount) ->
            val percentage = (amount / grandTotal) * 100
            val color = chartColors[colorIndex % chartColors.size]

            // Main Brown Capsule Container Bar
            val barLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
                setPadding(24, 16, 24, 16)
                background = GradientDrawable().apply {
                    shape = GradientDrawable.RECTANGLE
                    cornerRadius = 50f
                    setColor(Color.parseColor("#725030")) // Capsule brown color
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 12, 0, 12)
                }
            }

            // Circular Color Dot Indicator
            val colorDot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(36, 36).apply {
                    setMargins(0, 0, 20, 0)
                }
                background = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(color)
                }
            }

            // Category Title + Percentage Text
            val categoryText = TextView(this).apply {
                text = String.format(Locale.ENGLISH, "%s - %.2f%%", category, percentage)
                textSize = 15f
                        setTextColor(Color.WHITE)
                setTypeface(null, android.graphics.Typeface.BOLD)
            }

            barLayout.addView(colorDot)
            barLayout.addView(categoryText)
            legendContainer.addView(barLayout)

            colorIndex++
        }
    }
}