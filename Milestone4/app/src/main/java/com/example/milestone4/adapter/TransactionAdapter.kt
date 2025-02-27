package com.example.milestone4.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.milestone4.databinding.ItemTransactionBinding
import com.example.milestone4.model.Transaction


class TransactionAdapter(
     private var transactions: List<Transaction>,
private val onClick: (Transaction) -> Unit
) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.binding.apply {
            name.text = "Name: ${transaction.name}"
            category.text = "Category: ${transaction.category}"
            amount.text =  "Amount: ${transaction.amount}"
            root.setOnClickListener { onClick(transaction) }
        }
    }
}


