package com.example.psm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.DataDBlite
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MascotasAdminActivity : AppCompatActivity() {

    // Declara la lista fuera del método onCreate para que sea accesible en otros métodos
    private var especies: List<ApiResponseEspecies> = listOf()
    private var especieSeleccionada: ApiResponseEspecies? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas_admin)

        val btnAgregarEspecie = findViewById<AppCompatButton>(R.id.btnConfirmarEspecie)


        val btnEditarEspecie = findViewById<AppCompatButton>(R.id.btnEditarEspecie)

        val btnEliminarEspecie = findViewById<AppCompatButton>(R.id.btnEliminarEspecie)

        btnAgregarEspecie.setOnClickListener {
            var bEspecie = findViewById<EditText>(R.id.editTextEspecie)

            var especie = bEspecie.text.toString()

            if(especie == ""){
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Favor de llenar la especie.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiResponseInsertEspecie(especie)
            Log.e("exito", "Perfecto")
            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    if (response.isSuccessful) {
                        recreate()
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        btnEditarEspecie.setOnClickListener {
            var bEspecie = findViewById<EditText>(R.id.editTextEspecieE)

            var especie = bEspecie.text.toString()

            if(especie == ""){
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Favor de llenar la especie.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiModificarEspecie(especie, especieSeleccionada!!.idEspecie)
            Log.e("exito", "Perfecto")
            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    if (response.isSuccessful) {
                        recreate()
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }


        btnEliminarEspecie.setOnClickListener {

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiEliminarESPECIE(especieSeleccionada!!.idEspecie)
            Log.e("exito", "Perfecto")
            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    if (response.isSuccessful) {
                        recreate()
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        val spinner: Spinner = findViewById(R.id.spinnerMascot)

        val apiInterface = RetrofitInstance.instance

        // Llamada a la API para obtener las especies
        apiInterface.getApiEspecies().enqueue(object: Callback<List<ApiResponseEspecies>> {
            override fun onResponse(call: Call<List<ApiResponseEspecies>>, response: Response<List<ApiResponseEspecies>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
                    // Guardar la lista de especies
                    especies = response.body()!!

                    // Mapear la lista de especies a una lista de nombres
                    val especiesNombres = especies.map { it.nombre }

                    // Crear un ArrayAdapter usando la lista de nombres
                    val adapter = ArrayAdapter<String>(
                        this@MascotasAdminActivity,
                        android.R.layout.simple_spinner_item,
                        especiesNombres
                    )

                    // Asignar el ArrayAdapter al Spinner
                    spinner.adapter = adapter
                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseEspecies>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las especies", t)
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (especies.isNotEmpty()) {
                    especieSeleccionada = especies[position]
                } else {
                    Log.d("ID_ESPECIE", "La lista de especies está vacía")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }
}