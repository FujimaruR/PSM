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

class DoctoresFragment : Fragment() {
    // TODO: Rename and change types of parameters


    // Declara la lista fuera del método onCreate para que sea accesible en otros métodos
    private var doctores: List<ApiResponseDoctores> = listOf()
    private var doctorSeleccionado: ApiResponseDoctores? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_doctores, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAgregarDoctor = view.findViewById<AppCompatButton>(R.id.btnConfirmarDoctor)
        val btnEditarDoctor = view.findViewById<AppCompatButton>(R.id.btnEditarDoctor)
        val btnEliminarDoctor = view.findViewById<AppCompatButton>(R.id.btnEliminarDoctor)

        btnAgregarDoctor.setOnClickListener {
            var bDoctor = view.findViewById<EditText>(R.id.editTextEspecie)

            var doctor = bDoctor.text.toString()
            var activo = 1;

            if(doctor == ""){
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Favor de llenar el nombre del Doctor.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiResponseInsertDoctor(doctor, activo)
            Log.e("exito", "Perfecto")
            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Log.e("EXITO EN LA OPERACION", "PERFECTO EL EXITO")
                        Toast.makeText(requireContext(), "Se ha creado el doctor", Toast.LENGTH_SHORT).show()


                    } else {
                        // Realizar alguna otra acción si el resultado no es "true"
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        btnEditarDoctor.setOnClickListener {
            var bDoctores = view.findViewById<EditText>(R.id.editTextDoctor)

            var doctor = bDoctores.text.toString()

            if(doctor == ""){
                val alertDialog = AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Favor de llenar la especie.")
                    .setPositiveButton("Aceptar", null)
                    .create()
                alertDialog.show()
                return@setOnClickListener
            }

            var activo = 1;
            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiModificarDoctor(doctor, activo, doctorSeleccionado!!.idDoctor)

            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Toast.makeText(requireContext(), "Se ha podido modificar el doctor", Toast.LENGTH_SHORT).show()

                    } else {
                        Log.e("exito", "Perfecto")
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }


        btnEliminarDoctor.setOnClickListener {

            val apiInterface = RetrofitInstance.instance
            val call: Call<ApiRes> = apiInterface.getApiEliminarDoctor(doctorSeleccionado!!.idDoctor)
            Log.e("exito", "Perfecto")
            call.enqueue(object : Callback<ApiRes> {
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    val apiResponse: ApiRes? = response.body()
                    if (apiResponse?.resultado == "true") {
                        Toast.makeText(requireContext(), "Se ha podido borrar el doctor", Toast.LENGTH_SHORT).show()
                        // REINICIAR
                    } else {
                    }

                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())

                }
            })

        }

        val spinner: Spinner = view.findViewById(R.id.spinnerDoctores)

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
                        requireContext(),
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
                } else {
                    Log.d("ID_Doctores", "La lista de especies está vacía")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }


    }
}