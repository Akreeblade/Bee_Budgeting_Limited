package com.example.beebudgetinglimited

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

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
        val db = FirebaseFirestore.getInstance()
        val categoriesRef = db.collection("categories")

        // 1. Fetch categories live from Firestore
        categoriesRef.get()
            .addOnSuccessListener { result ->
                val loadedCategories = mutableListOf<Category>()

                // If the Firestore "categories" collection is completely empty,
                // let's seed your default presets automatically so the app isn't blank!
                if (result.isEmpty) {
                    val defaultPresets = listOf(
                        Category("Dining out", isIncome = false),
                        Category("Gifts", isIncome = false),
                        Category("Groceries", isIncome = false),
                        Category("Entertainment", isIncome = false),
                        Category("Shopping", isIncome = false),
                        Category("Transport", isIncome = false),
                        Category("Bills", isIncome = false),
                        Category("Salary", isIncome = true),
                        Category("Freelance", isIncome = true),
                        Category("Investments", isIncome = true)
                    )
                    for (preset in defaultPresets) {
                        categoriesRef.add(preset)
                        loadedCategories.add(preset)
                    }
                } else {
                    // Pull whatever is in Firestore (defaults + any custom ones added by users)
                    for (document in result) {
                        val name = document.getString("name") ?: continue
                        val isIncome = document.getBoolean("isIncome") ?: false
                        loadedCategories.add(Category(name = name, isIncome = isIncome))
                    }
                }

                // 2. Filter based on whether user is logging Income or Expense
                val filteredCategories = loadedCategories.filter { it.isIncome == isIncomeOnly }
                val headerText = if (isIncomeOnly) "Income Categories" else "Expense Categories"

                categoryList = mutableListOf<CategoryItem>().apply {
                    add(CategoryItem(name = "Header", isHeader = true, headerTitle = headerText))
                    addAll(filteredCategories.map { CategoryItem(name = it.name) })
                }

                // 3. Render Grid Layout
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
            .addOnFailureListener {
                Toast.makeText(this, "Failed to load categories from database", Toast.LENGTH_SHORT).show()
            }
    }

        /*// Retrieve and filter categories matching selection mode
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
        }*/

}