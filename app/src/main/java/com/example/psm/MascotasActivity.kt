package com.example.psm

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.data.MascotaAdapter
import com.example.psm.data.MyMascotasList
import com.example.psm.data.idMascotaSingleton
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MascotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mascotaAdapter: MascotaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas)

        val btneditaru = findViewById<AppCompatButton>(R.id.botonEditarMascota)
        val btnagregarmas = findViewById<AppCompatButton>(R.id.boton5)
        val btnEliminarMas = findViewById<AppCompatButton>(R.id.botonEliminarMascota)


        recyclerView = findViewById(R.id.mascotas_recyclerviewer)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Llena la lista con datos de la base de datos
        val apiiN = RetrofitInstance.instance

        val call: Call<List<ApiResponseMascotas>> = apiiN.getApiMascotas(userSingleton.currentUserId)
        call.enqueue(object : Callback<List<ApiResponseMascotas>> {
            override fun onResponse(call: Call<List<ApiResponseMascotas>>, response: Response<List<ApiResponseMascotas>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful) {
                    Log.e("API_CORRECTO", "Se pudieron obtener las mascotas")
                    MyMascotasList.myMList.clear()
                    val listaApiResponse: List<ApiResponseMascotas>? = response.body()
                    listaApiResponse?.forEach{ mascotas ->
                        val bitmap: ByteArray = Base64.decode(mascotas.imagen, Base64.DEFAULT)
                        val listasMascotas = listaApiResponse.map { apiResponse ->
                            MascotasModel(
                                idMascota = apiResponse.idMascota ?: 0, // Valor predeterminado si no está disponible en la API
                                nombre = apiResponse.nombreMascota ?: "",
                                activo = apiResponse.activoMascota ?: 0,
                                edad = apiResponse.edad ?: 0,
                                idEspecie = apiResponse.idEspecie ?: 0,
                                idUsuario = apiResponse.idUsuario ?: 0,
                                img1 = bitmap,
                                nomEspecie = apiResponse.nombreEspecie ?: "",
                                raza = apiResponse.raza ?: ""
                            )
                        }
                        MyMascotasList.myMList.clear()
                        MyMascotasList.myMList.addAll(listasMascotas)
                    }

                    // Crea el adaptador y asigna al RecyclerView
                    mascotaAdapter = MascotaAdapter(MyMascotasList.myMList)
                    recyclerView.adapter = mascotaAdapter

                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseMascotas>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las mascotas", t)
            }
        })

        btneditaru.setOnClickListener {
            val intentse = Intent(this, EditarMascotaActivity::class.java)
            startActivity(intentse)
        }

        btnagregarmas.setOnClickListener {
            val intento = Intent(this, MascotasAddActivity::class.java)
            startActivity(intento)
        }

        btnEliminarMas.setOnClickListener {
            idMascotaSingleton.currentMascotaId = 1

            val apiiN = RetrofitInstance.instance

            val call: Call<ApiRes> = apiiN.getApiDeleteMascotas(idMascotaSingleton.currentMascotaId)

            Log.e("Prueba1:", "Despues de registrar")

            call.enqueue(object : Callback<ApiRes> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    Log.e("Prueba2:", "Si jalooooooooo")
                    if (response.isSuccessful) {
                        val apiResponse: ApiRes? = response.body()
                        Log.e("exito", "Perfecto")

                        credenciales()
                    } else{
                        Log.e("Error en la solicitud respuesta:", "No jalo")
                        onError()
                    }
                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud api:", t.message.toString())
                    onError()
                    val errorBody = call.request().body?.toString()
                    Log.e("Respuesta del servidor (error):", errorBody ?: "Error body is null")
                }
            })
        }
    }

    fun credenciales(){
        if(userSingleton.currentUserName!!.isNotEmpty()){
            val intentd = Intent(this, MascotasFragment::class.java)
            startActivity(intentd)
        } else{
            Toast.makeText(this, "Error al registrar Mascota", Toast.LENGTH_SHORT).show()
        }
    }

    fun onError(){
        Toast.makeText(this, "Error en la operacion", Toast.LENGTH_SHORT).show()
    }
}