package com.example.beebudgetinglimited

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.net.Uri

class TransactionAdapter(private val transactionList: List<TransactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        val tvNote: TextView = itemView.findViewById(R.id.tvTransactionNote)
        val tvAmount: TextView = itemView.findViewById(R.id.tvTransactionAmount)

        val ivPhoto: ImageView = itemView.findViewById(R.id.ivTransactionPhoto)
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

        //handles images
        if (!item.photoUri.isNullOrEmpty()) {
            holder.ivPhoto.visibility = View.VISIBLE
            holder.ivPhoto.setImageURI(Uri.parse(item.photoUri))
        } else {
            holder.ivPhoto.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = transactionList.size
}