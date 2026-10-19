package com.example.beebudgetinglimited

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object TransactionRepository {

    private val transactions = mutableListOf<TransactionItem>()

    fun getTransactions(): List<TransactionItem> = transactions

    // Async function to fetch live transactions from Firestore
    suspend fun fetchTransactionsFromFirestore(): List<TransactionItem> {
        return try {
            val db = FirebaseFirestore.getInstance()
            val result = db.collection("transactions").get().await()

            transactions.clear()
            for (document in result) {
                val item = document.toObject(TransactionItem::class.java)
                transactions.add(item)
            }
            transactions.sortByDescending { it.date } // Optional: sort by newest
            transactions
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    fun addTransaction(item: TransactionItem) {
        transactions.add(0, item) // Adds locally for instant UI feedback
    }
}