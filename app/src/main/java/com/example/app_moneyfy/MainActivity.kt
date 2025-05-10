package com.example.app_moneyfy

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.transaction_button)
        
        fab.setOnClickListener {
            val intent = Intent(this, CrudTransactionActivity::class.java)
            startActivity(intent)
        }
    }
}