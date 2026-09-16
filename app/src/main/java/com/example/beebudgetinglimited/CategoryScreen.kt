package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// 1. Data class defined right here so Kotlin always recognizes it
data class CategoryItem(
    val name: String,
    val isHeader: Boolean = false,
    val headerTitle: String = ""
)

class CategoryScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_screen)

        val recyclerView = findViewById<RecyclerView>(R.id.rvCategories)

        // 2. Category list now resolves properly without type inference errors
        val categoryList = listOf(
            CategoryItem(name = "Header", isHeader = true, headerTitle = "Expense Categories"),
            CategoryItem("Dining out"),
            CategoryItem("Gifts"),
            CategoryItem("Groceries"),
            CategoryItem("Entertainment"),
            CategoryItem("Shopping"),
            CategoryItem("Transport"),
            CategoryItem("Bills"),

            CategoryItem(name = "Header", isHeader = true, headerTitle = "Income Categories"),
            CategoryItem("Salary"),
            CategoryItem("Freelance"),
            CategoryItem("Investments")
        )

        // Set up GridLayoutManager with 3 columns
        val gridLayoutManager = GridLayoutManager(this, 3)

        // Ensures headers stretch across all 3 columns, while categories take 1 column each
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (categoryList[position].isHeader) 3 else 1
            }
        }

        recyclerView.layoutManager = gridLayoutManager

        recyclerView.adapter = CategoryAdapter(categoryList) { selectedCategory ->
            // Create an intent to send the category name back
            val resultIntent = Intent().apply {
                putExtra("SELECTED_CATEGORY", selectedCategory)
            }
            setResult(RESULT_OK, resultIntent)
            finish() // Closes CategoryScreen and returns to LoggingScreen
        }

        findViewById<TextView>(R.id.tvCreateNewCategory).setOnClickListener {
            val intent = Intent(this@CategoryScreen, AddCategoryScreen::class.java)
            startActivity(intent)
            finish()

        }
    }
}