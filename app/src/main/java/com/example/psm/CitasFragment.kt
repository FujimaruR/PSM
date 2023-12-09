package com.example.psm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.AppCompatImageButton
import android.content.Intent
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.data.CitasAdapter
import com.example.psm.data.MascotaAdapter
import com.example.psm.data.MyCitasList
import com.example.psm.data.MyMascotasList
import com.example.psm.data.idCitaSingleton
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasFragment : Fragment(), CitasAdapter.CitaClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var citasAdapter: CitasAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_citas, container, false)*/
        val idCita = idCitaSingleton.currentCitaId
        val root = inflater.inflate(R.layout.fragment_citas, container, false)

        recyclerView = root.findViewById(R.id.citas_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this.requireContext())

        val apiiN = RetrofitInstance.instance

        apiiN.getApiCitas(userSingleton.currentUserId).enqueue(object: Callback<List<ApiResponseCitas>> {
            override fun onResponse(call: Call<List<ApiResponseCitas>>, response: Response<List<ApiResponseCitas>>) {

                if (response.isSuccessful && response.body() != null) {
                    Log.e("API_CORRECTO", "Se pudieron obtener las citas")

                    val listaApiResponse: List<ApiResponseCitas>? = response.body()
                    listaApiResponse?.forEach{ citas ->
                        val listasMascotas = listaApiResponse.map { apiResponse ->
                            CitasModel(
                                IdCita = apiResponse.IdCita ?:0,
                                FechaCita = apiResponse.FechaCita ?: "",
                                HoraCita = apiResponse.HoraCita ?: "",
                                NombreMascota = apiResponse.NombreMascota ?: "",
                                RazaMascota = apiResponse.RazaMascota ?: "",
                                NombreDoctor = apiResponse.NombreDoctor
                            )
                        }
                        MyCitasList.myMList.clear()
                        MyCitasList.myMList.addAll(listasMascotas)
                    }
                    citasAdapter = CitasAdapter(MyCitasList.myMList)
                    citasAdapter.listener = this@CitasFragment
                    recyclerView.adapter = citasAdapter

                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las citas")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseCitas>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las citas", t)
            }
        })

        return root
    }

    // Aquí podrías, por ejemplo, inicializar tus vistas, configurar escuchadores de eventos, etc.

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAgregarCita: AppCompatImageButton = view.findViewById(R.id.buttonAgregarCita)

        // Configura el oyente de clics para buttonAgregarCita
        buttonAgregarCita.setOnClickListener {
            // Crea una nueva Intent para abrir AddCita
            val intent = Intent(activity, AddCita::class.java)

            // Inicia la actividad
            startActivity(intent)
        }

    }

    override fun onEditarMascotaClick(cita: CitasModel) {
        Log.e("ID MAMALON EDITADO", cita.IdCita.toString())
    }

    override fun onEliminarMascotaClick(cita: CitasModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("¿Estás seguro de que deseas eliminar esta cita?")
            .setPositiveButton("Sí") { dialog, which ->
                // Lógica para eliminar la mascota
                Log.e("ENTRE ACA JAJA EN ELIMINAR CITA", cita.IdCita.toString())
            }
            .setNegativeButton("No") { dialog, which ->
                Log.e("no elimine la cita", cita.IdCita.toString())
            }
            .show()
    }
}