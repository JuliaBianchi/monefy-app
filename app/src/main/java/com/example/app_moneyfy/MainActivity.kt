package com.example.app_moneyfy
import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_moneyfy.databinding.ActivityMainBinding
import database.DatabaseHandler
import entity.Transaction


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db: DatabaseHandler

    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListTextView: TextView
    private lateinit var balanceValueTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(this)

        recyclerView =  binding.recyclerViewTransactions
        emptyListTextView = binding.textViewEmptyList

        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionsAdapter = TransactionsAdapter(mutableListOf())
        recyclerView.adapter = transactionsAdapter
        balanceValueTextView = binding.balanceValue

        val fab = binding.transactionButton

        fab.setOnClickListener {
            val intent = Intent(this, CrudTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTransactionsAndCalculateBalance()
    }

    @SuppressLint("SetTextI18n")
    private fun loadTransactionsAndCalculateBalance() {
        val transactionsList: List<Transaction> = db.list()

        var currentBalance = 0.0

        for (transaction in transactionsList) {
            if (transaction.type.equals(TYPE_RECEITA, ignoreCase = true)) {
                currentBalance += transaction.value
            } else if (transaction.type.equals(TYPE_DESPESA, ignoreCase = true)) {
                currentBalance -= transaction.value
            }
        }

        val currencyFormatter = NumberFormat.getCurrencyInstance()
        balanceValueTextView.text = currencyFormatter.format(currentBalance)

        if (transactionsList.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyListTextView.visibility = View.VISIBLE
            emptyListTextView.text = "Nenhuma transação encontrada."
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyListTextView.visibility = View.GONE
            transactionsAdapter.updateData(transactionsList)
        }

        Log.d("MainActivity", "Saldo Calculado: $currentBalance")
        Log.d("MainActivity", "Número de Transações: ${transactionsList.size}")
    }

    companion object {
        const val TYPE_RECEITA = "Crédito"
        const val TYPE_DESPESA = "Débito"
    }
}

