package com.example.app_moneyfy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import database.DatabaseHandler


class MainActivity : AppCompatActivity() {

    private lateinit var db: DatabaseHandler
    private lateinit var transactionsAdapter: TransactionsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        db = DatabaseHandler(this)

        recyclerView = findViewById(R.id.recycler_view_transactions)
        emptyListTextView = findViewById(R.id.text_view_empty_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        transactionsAdapter = TransactionsAdapter(mutableListOf())
        recyclerView.adapter = transactionsAdapter

        val fab = findViewById<FloatingActionButton>(R.id.transaction_button)

        fab.setOnClickListener {
            val intent = Intent(this, CrudTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadTransactions()
    }

    private fun loadTransactions() {
        val transactions = db.list()

        if (transactions.isEmpty()) {
            recyclerView.visibility = View.GONE
            emptyListTextView.visibility = View.VISIBLE
            emptyListTextView.text = "Nenhum lançamento encontrado."
        } else {
            recyclerView.visibility = View.VISIBLE
            emptyListTextView.visibility = View.GONE
            transactionsAdapter.updateData(transactions)
        }
    }
}