package com.example.app_moneyfy

import entity.Transaction
import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app_moneyfy.databinding.ActivityCrudTransactionBinding
import database.DatabaseHandler
import java.util.Calendar
import kotlin.text.*

class CrudTransactionActivity : AppCompatActivity() {


    private lateinit var binding : ActivityCrudTransactionBinding
    private lateinit var db: DatabaseHandler

    @SuppressLint("MissingInflatedId", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCrudTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHandler(this)


        val  types = listOf<String>("Selecione","Débito", "Crédito")
        val adapterTypes = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, types)

        val  details = mutableListOf<String>("Selecione")
        val adapterDetails = ArrayAdapter(this, R.layout.simple_spinner_dropdown_item, details)

        binding.spinnerType.adapter = adapterTypes
        binding.spinnerDetails.adapter = adapterDetails

        binding.backButton.setOnClickListener {
            finish()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {


                if (binding.spinnerType.selectedItem.toString() == "Débito") {
                    details.clear()
                    details.add("Selecione:")
                    details.add("Alimentação")
                    details.add("Transporte")
                    details.add("Saúde ")
                    details.add("Moradia")
                }else{
                    details.clear()
                    details.add("Selecione:")
                    details.add("Salário")
                    details.add("Extras")
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        binding.spinnerDetails.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }

        binding.editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                    binding.editTextDate.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }

        binding.buttonSave.setOnClickListener {
            buttonSaveOnClick()
        }


    }

    private fun buttonSaveOnClick() {
        val transaction = Transaction(
            0,
            binding.spinnerType.selectedItem.toString(),
            binding.spinnerDetails.selectedItem.toString(),
            binding.etValue.text.toString().toDouble(),
            binding.editTextDate.text.toString(),

        )

        db.insert(transaction)

        finish()

        Toast.makeText(this, "Registro inserido com sucesso", Toast.LENGTH_LONG).show()
    }
}

