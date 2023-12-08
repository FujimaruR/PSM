package com.example.psm

import android.app.TimePickerDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import java.util.Calendar
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class AddCita : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cita)
        supportActionBar?.hide()

        val editTextFecha = findViewById<EditText>(R.id.editTextNombre)
        val editTextHora = findViewById<EditText>(R.id.spinnerFecha)

        val btnreturn = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)
        val btnconfirm = findViewById<EditText>(R.id.GuardarMasc)

        // Obtiene la fecha actual
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Muestra el DatePickerDialog cuando el campo de texto se hace clic
        editTextFecha.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // El usuario ha seleccionado una fecha
                    val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                    editTextFecha.setText(fechaSeleccionada)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        editTextHora.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    // El usuario ha seleccionado una hora
                    val horaSeleccionada = "$hourOfDay:$minute"
                    editTextHora.setText(horaSeleccionada)
                },
                hour,
                minute,
                true // true para usar el formato de 24 horas, false para usar el formato de 12 horas
            )
            timePickerDialog.show()
        }

        btnreturn.setOnClickListener {
            val intent = Intent(this, CitasFragment::class.java)
            startActivity(intent)
        }

        btnconfirm.setOnClickListener {
            val intentd = Intent(this, CitasFragment::class.java)
            startActivity(intentd)
        }
    }
}