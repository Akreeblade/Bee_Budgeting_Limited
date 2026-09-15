package com.example.beebudgetinglimited

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TransactionAdapter(private val transactionList: List<TransactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        val tvNote: TextView = itemView.findViewById(R.id.tvTransactionNote)
        val tvAmount: TextView = itemView.findViewById(R.id.tvTransactionAmount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_transaction, parent, false)
        return TransactionViewHolder(view)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = transactionList[position]
        holder.tvTitle.text = item.title
        holder.tvAmount.text = item.amount

        // Handle optional notes
        if (!item.note.isNullOrEmpty()) {
            holder.tvNote.text = "Note: ${item.note}"
            holder.tvNote.visibility = View.VISIBLE
        } else {
            holder.tvNote.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = transactionList.size
}