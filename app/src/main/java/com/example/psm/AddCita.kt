package com.example.psm

import android.app.TimePickerDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import java.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCita : AppCompatActivity() {

    private var doctores: List<ApiResponseDoctores> = listOf()
    private var doctorSeleccionado: ApiResponseDoctores? = null

    private var mascotas: List<ApiResponseMascotas> = listOf()
    private var mascotaSeleccionada: ApiResponseMascotas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cita)
        supportActionBar?.hide()

        val editTextFecha = findViewById<EditText>(R.id.editTextNombre)
        val editTextHora = findViewById<EditText>(R.id.spinnerFecha)

        val btnreturn = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)
        val btnconfirm = findViewById<EditText>(R.id.guardarCita)

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
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

        btnconfirm.setOnClickListener {
            val hora = editTextHora.text.toString()
            val idMascota = mascotaSeleccionada?.idMascota
            val idUsuario = userSingleton.currentUserId
            val idDoctor = doctorSeleccionado?.idDoctor
            val activo = 1
            val fecha = editTextFecha.text.toString()

            val apiInterface = RetrofitInstance.instance

            apiInterface.getApiInsertCita(hora, idMascota, idUsuario, idDoctor, activo, fecha).enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    if (response.isSuccessful) {
                        Log.d("API_SUCCESS", "Cita insertada exitosamente")
                    } else {
                        Log.e("API_ERROR", "No se pudo insertar la cita")
                    }
                }

                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("API_ERROR", "Error al llamar a la API para insertar la cita", t)
                }
            })
        }


        val spinner: Spinner = findViewById(R.id.spinnerDoctor)

        val apiInterface = RetrofitInstance.instance

        // Llamada a la API para obtener las especies
        apiInterface.getApiDoctores().enqueue(object: Callback<List<ApiResponseDoctores>> {
            override fun onResponse(call: Call<List<ApiResponseDoctores>>, response: Response<List<ApiResponseDoctores>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
                    // Guardar la lista de especies
                    doctores = response.body()!!

                    // Mapear la lista de especies a una lista de nombres
                    val doctoresNombres = doctores.map { it.nombre }

                    // Crear un ArrayAdapter usando la lista de nombres
                    val adapter = ArrayAdapter<String>(
                        this@AddCita,
                        android.R.layout.simple_spinner_item,
                        doctoresNombres
                    )

                    // Asignar el ArrayAdapter al Spinner
                    spinner.adapter = adapter
                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseDoctores>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las especies", t)
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (doctores.isNotEmpty()) {
                    doctorSeleccionado = doctores[position]
                    Log.d("ID_Doctores", doctorSeleccionado.toString())
                } else {
                    Log.d("ID_Doctores", "La lista de doctores está vacía")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }


        val spinner2: Spinner = findViewById(R.id.spinnerMascot)

        var idUsuario = userSingleton.currentUserId

        apiInterface.getApiMascotas(idUsuario).enqueue(object: Callback<List<ApiResponseMascotas>> {
            override fun onResponse(call: Call<List<ApiResponseMascotas>>, response: Response<List<ApiResponseMascotas>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
                    // Guardar la lista de especies
                    mascotas = response.body()!!

                    // Mapear la lista de especies a una lista de nombres
                    val mascotasNombres = mascotas.map { it.nombreMascota }

                    // Crear un ArrayAdapter usando la lista de nombres
                    val adapter = ArrayAdapter<String>(
                        this@AddCita,
                        android.R.layout.simple_spinner_item,
                        mascotasNombres
                    )

                    // Asignar el ArrayAdapter al Spinner
                    spinner2.adapter = adapter
                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseMascotas>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las especies", t)
            }
        })

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (mascotas.isNotEmpty()) {
                    mascotaSeleccionada = mascotas[position]
                    Log.d("ID_Mascota", mascotaSeleccionada.toString())
                } else {
                    Log.d("ID_Doctores", "La lista de doctores está vacía")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }


    }


}