package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.data.MascotaAdapter
import androidx.appcompat.widget.AppCompatImageButton
import com.example.psm.data.userSingleton
import retrofit2.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.psm.data.MyMascotasList
import retrofit2.Callback
import retrofit2.Response

class MascotasFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mascotaAdapter: MascotaAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val root = inflater.inflate(R.layout.fragment_mascotas, container, false)


        val idUse = userSingleton.currentUserId
        val btnagregarmas = root.findViewById<AppCompatImageButton>(R.id.buttonAgregarMascota)

        recyclerView = root.findViewById(R.id.mascotas_recyclerviewer)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        // Llena la lista con datos de la base de datos
        val apiiN = RetrofitInstance.instance

        apiiN.getApiMascotas(idUse).enqueue(object: Callback<List<ApiResponseMascotas>> {
            override fun onResponse(call: Call<List<ApiResponseMascotas>>, response: Response<List<ApiResponseMascotas>>) {

                if (response.isSuccessful && response.body() != null) {
                    Log.e("API_CORRECTO", "Se pudieron obtener las mascotas")
                    Log.e("API_CORRECTO", "Datos de respuesta: " + response.body().toString())
                }

                    // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
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
                    MyMascotasList.myMList += listasMascotas
                }
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

        btnagregarmas.setOnClickListener {
            val intento = Intent(this.requireContext(), MascotasAddActivity::class.java)
            startActivity(intento)
        }


        return root
    }
}