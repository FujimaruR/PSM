package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.data.MascotaAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MascotasActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mascotaAdapter: MascotaAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascotas)

        val btninicio = findViewById<AppCompatButton>(R.id.botonInicio)

        val btnmascotas = findViewById<AppCompatButton>(R.id.botonMascotas)
        val btncitas = findViewById<AppCompatButton>(R.id.botonCitas)
        val btnperfil = findViewById<AppCompatButton>(R.id.botonPerfil)

        val btneditaru = findViewById<AppCompatButton>(R.id.botonEditarMascota)
        val btneditard = findViewById<AppCompatButton>(R.id.botonEditarMascotad)
        val btnagregarmas = findViewById<AppCompatButton>(R.id.boton5)

        /*recyclerView = findViewById(R.id.MascotaAdapter)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Llena la lista con datos de la base de datos
        val apiiN = RetrofitInstance.instance

        apiiN.getApiMascotas().enqueue(object: Callback<List<ApiResponseMascotas>> {
            override fun onResponse(call: Call<List<ApiResponseMascotas>>, response: Response<List<ApiResponseMascotas>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
                    Log.e("API_CORRECTO", "Se pudieron obtener las especies")
                    // Mapear la lista de respuesta a una lista de nombres de las especies
                    val especiesNombres = response.body()!!.map { it.nombre }

                    // Crear un ArrayAdapter usando la lista de nombres
                    val adapter = ArrayAdapter<String>(
                        this@MascotasAddActivity,
                        android.R.layout.simple_spinner_item,
                        especiesNombres
                    )

                    // Asignar el ArrayAdapter al Spinner
                    spinner.adapter = adapter

                    especies = response.body()!!

                    Log.d("ESPECIES_SIZE", "Número de especies: ${especies.size}")
                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseEspecies>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las especies", t)
            }
        })

        val listaMascotas = obtenerDatosDeLaBaseDeDatos()

        // Crea el adaptador y asigna al RecyclerView
        mascotaAdapter = MascotaAdapter(listaMascotas)
        recyclerView.adapter = mascotaAdapter*/

        btninicio.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }



        btnmascotas.setOnClickListener {
            val intentt = Intent(this, MascotasActivity::class.java)
            startActivity(intentt)
        }

        btncitas.setOnClickListener {
            val intentc = Intent(this, CitasFragment::class.java)
            startActivity(intentc)
        }

        btnperfil.setOnClickListener {
            val intents = Intent(this, ProfileActivity::class.java)
            startActivity(intents)
        }

        btneditaru.setOnClickListener {
            val intentse = Intent(this, EditarMascotaActivity::class.java)
            startActivity(intentse)
        }

        btneditard.setOnClickListener {
            val intentsi = Intent(this, EditarMascotaActivity::class.java)
            startActivity(intentsi)
        }

        btnagregarmas.setOnClickListener {
            val intento = Intent(this, MascotasAddActivity::class.java)
            startActivity(intento)
        }
    }
}