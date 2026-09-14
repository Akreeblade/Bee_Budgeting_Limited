package com.example.beebudgetinglimited

data class Expense(
    val expenseId: String = "",
    val userId: String = "",
    val price: Double = 0.0,
    val category: String = "")
