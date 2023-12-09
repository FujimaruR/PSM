package com.example.psm

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.DataDBlite
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EspeciesFragment : Fragment() {
    // TODO: Rename and change types of parameters


    // Declara la lista fuera del método onCreate para que sea accesible en otros métodos
    private var especies: List<ApiResponseEspecies> = listOf()
    private var especieSeleccionada: ApiResponseEspecies? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_especies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAgregarEspecie = view.findViewById<AppCompatButton>(R.id.btnConfirmarEspecie)
        val btnEditarEspecie = view.findViewById<AppCompatButton>(R.id.btnEditarEspecie)
        val btnEliminarEspecie = view.findViewById<AppCompatButton>(R.id.btnEliminarEspecie)

        btnAgregarEspecie.setOnClickListener {
            var bEspecie = view.findViewById<EditText>(R.id.editTextEspecie)

            var especie = bEspecie.text.toString()

            if(especie == ""){
                val alertDialog = AlertDialog.Builder(requireContext())
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
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Toast.makeText(requireContext(), "Se ha podido crear la especie", Toast.LENGTH_SHORT).show()
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        btnEditarEspecie.setOnClickListener {
            var bEspecie = view.findViewById<EditText>(R.id.editTextEspecieE)

            var especie = bEspecie.text.toString()

            if(especie == ""){
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Favor de llenar la especie.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiModificarEspecie(especie, especieSeleccionada!!.idEspecie)

            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Toast.makeText(requireContext(), "Se ha podido modificar la especie", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e("exito", "Perfecto")
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
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Toast.makeText(requireContext(), "Se ha podido eliminar la especie", Toast.LENGTH_SHORT).show()
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        val spinner: Spinner = view.findViewById(R.id.spinnerMascot)

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
                        requireContext(),
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