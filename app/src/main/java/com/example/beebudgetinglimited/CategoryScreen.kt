package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

data class CategoryItem(
    val name: String,
    val isHeader: Boolean = false,
    val headerTitle: String = ""
)

class CategoryScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var isIncomeOnly: Boolean = true
    private var categoryList: List<CategoryItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_screen)

        // Read intent flag passed from LoggingScreen (defaults to true if null)
        isIncomeOnly = intent.getBooleanExtra("IS_INCOME", true)

        recyclerView = findViewById(R.id.rvCategories)

        findViewById<TextView>(R.id.tvCreateNewCategory).setOnClickListener {
            val intent = Intent(this, AddCategoryScreen::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        // Retrieve and filter categories matching selection mode
        val filteredCategories = CategoryRepository.getAllCategories()
            .filter { it.isIncome == isIncomeOnly }

        val headerText = if (isIncomeOnly) "Income Categories" else "Expense Categories"

        // Store list in class variable so SpanSizeLookup can access it
        categoryList = mutableListOf<CategoryItem>().apply {
            add(CategoryItem(name = "Header", isHeader = true, headerTitle = headerText))
            addAll(filteredCategories.map { CategoryItem(name = it.name) })
        }

        // Use this@CategoryScreen to fix the context mismatch error
        val gridLayoutManager = GridLayoutManager(this@CategoryScreen, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (categoryList[position].isHeader) 3 else 1
            }
        }

        recyclerView.layoutManager = gridLayoutManager
        recyclerView.adapter = CategoryAdapter(categoryList) { selectedCategory ->
            val resultIntent = Intent().apply {
                putExtra("SELECTED_CATEGORY", selectedCategory)
            }
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}