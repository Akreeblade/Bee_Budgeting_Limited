package com.example.beebudgetinglimited

data class TransactionItem(
    val id: String = "",
    val userId: String = "",
    val title: String = "",      // e.g., "Dining out" or "Salary"
    val amount: Double = 0.0,     // e.g., "-R250" or "R1200"
    val note: String? = null,      // e.g., "Gift for mom" (optional)
    val date: String = "",       // e.g., "12 August 2026"
    val isIncome: Boolean = true,   // true for income, false for expense
    val photoUri: String? = null //optional photo
)
