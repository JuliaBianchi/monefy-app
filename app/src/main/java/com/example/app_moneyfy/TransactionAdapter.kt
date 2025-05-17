package com.example.app_moneyfy
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import entity.Transaction
import java.text.NumberFormat

class TransactionsAdapter(private var transactions: MutableList<Transaction>) :
    RecyclerView.Adapter<TransactionsAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return TransactionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val currentTransaction = transactions[position]

        val amount = currentTransaction.value
        val currencyFormatter = NumberFormat.getCurrencyInstance()

        holder.transactionId.text = currentTransaction.id.toString()
        holder.textViewLabel.text = currentTransaction.type
        holder.textViewDetails.text = currentTransaction.details
        holder.textViewValue.text = currencyFormatter.format(amount)
        holder.textViewDate.text = currentTransaction.date
    }

    override fun getItemCount() = transactions.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newTransactions: List<Transaction>) {
        transactions.clear()
        transactions.addAll(newTransactions)
        notifyDataSetChanged()
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val transactionId: TextView = itemView.findViewById(R.id.text_view_id)
        val textViewLabel: TextView = itemView.findViewById(R.id.text_view_label)
        val textViewDetails: TextView = itemView.findViewById(R.id.text_view_details)
        val textViewValue: TextView = itemView.findViewById(R.id.text_view_value)
        val textViewDate: TextView = itemView.findViewById(R.id.text_view_date)

    }
}