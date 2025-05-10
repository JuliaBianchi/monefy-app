package com.example.app_moneyfy

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar
import kotlin.text.*

class CrudTransactionActivity : AppCompatActivity() {

    private lateinit var spinnerType: Spinner
    private lateinit var spinnerDetails: Spinner

    private lateinit var etValue: EditText
    private lateinit var editTextDate: EditText



    @SuppressLint("MissingInflatedId", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_crud_transaction)


        val backButton = findViewById<Button>(R.id.back_button)
        val cancelButton = findViewById<Button>(R.id.cancelButton)

        spinnerType = findViewById(R.id.spinnerType)
        spinnerDetails = findViewById(R.id.spinnerDetails)

        editTextDate = findViewById(R.id.editTextDate)

        val  types = listOf<String>("Selecione","Débito", "Crédito")
        val adapterTypes = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, types)

        val  details = mutableListOf<String>("Selecione")
        val adapterDetails = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, details)

        spinnerType.adapter = adapterTypes
        spinnerDetails.adapter = adapterDetails


        backButton.setOnClickListener {
            finish()
        }

        cancelButton.setOnClickListener {
            finish()
        }

        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {


                if (spinnerType.selectedItem.toString() == "Débito") {
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
                TODO("Not yet implemented")
            }
        }

        spinnerDetails.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        editTextDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                    editTextDate.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePicker.show()
        }


    }
}

