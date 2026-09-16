package com.example.beebudgetinglimited

object CategoryRepository {

    private val categories = mutableListOf(
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

    fun getAllCategories(): List<Category> = categories

    fun addCategory(category: Category) {
        categories.add(category)
    }

    // Converts Category objects into CategoryItem lists with headers for your UI adapter
    fun getFormattedCategoryItems(): List<CategoryItem> {
        val items = mutableListOf<CategoryItem>()

        val expenses = categories.filter { !it.isIncome }
        if (expenses.isNotEmpty()) {
            items.add(CategoryItem(name = "Header", isHeader = true, headerTitle = "Expense Categories"))
            items.addAll(expenses.map { CategoryItem(name = it.name) })
        }

        val income = categories.filter { it.isIncome }
        if (income.isNotEmpty()) {
            items.add(CategoryItem(name = "Header", isHeader = true, headerTitle = "Income Categories"))
            items.addAll(income.map { CategoryItem(name = it.name) })
        }

        return items
    }
}