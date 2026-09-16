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
    private var categoryList: List<CategoryItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_screen)

        recyclerView = findViewById(R.id.rvCategories)

        findViewById<TextView>(R.id.tvCreateNewCategory).setOnClickListener {
            val intent = Intent(this, AddCategoryScreen::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload categories every time the screen becomes active
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        categoryList = CategoryRepository.getFormattedCategoryItems()

        val gridLayoutManager = GridLayoutManager(this, 3)
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