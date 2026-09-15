package com.example.beebudgetinglimited

data class TransactionItem(
    val title: String,      // e.g., "Dining out" or "Salary"
    val amount: String,     // e.g., "-R250" or "R1200"
    val note: String?,      // e.g., "Gift for mom" (optional)
    val date: String,       // e.g., "12 August 2026"
    val isIncome: Boolean   // true for income, false for expense
)
