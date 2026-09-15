package com.example.beebudgetinglimited

object TransactionRepository {
    private val transactions = mutableListOf(
        TransactionItem("Salary", "R1200", null, "12 August 2026", true),
        TransactionItem("Dining out", "-R250", null, "12 August 2026", false),
        TransactionItem("Gifts", "-R300", "Gift for mom", "12 August 2026", false),
        TransactionItem("Entertainment", "-R150", null, "30 July 2026", false),
        TransactionItem("Groceries", "-R300", null, "30 July 2026", false),
        TransactionItem("Salary", "R1000", null, "30 July 2026", true),
        TransactionItem("Online Shopping", "-R160", "New airfryer", "22 July 2026", false)
    )

    fun getTransactions(): List<TransactionItem> = transactions

    fun addTransaction(item: TransactionItem) {
        // Adds the new item to the very top of the list
        transactions.add(0, item)
    }
}