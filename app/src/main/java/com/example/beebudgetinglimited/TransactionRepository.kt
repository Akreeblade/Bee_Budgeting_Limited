package com.example.beebudgetinglimited

object TransactionRepository {

    private val transactions = mutableListOf<TransactionItem>()

    fun getTransactions(): List<TransactionItem> = transactions

    fun addTransaction(item: TransactionItem) {
        transactions.add(0, item) //adds logged stuff to top of list
    }
}