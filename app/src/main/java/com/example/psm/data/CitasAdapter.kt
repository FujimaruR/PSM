package com.example.psm.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.R
import com.example.psm.MascotasModel
import android.util.Log
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat.startActivity
import com.example.psm.ApiRes
import com.example.psm.CitasFragment
import com.example.psm.CitasModel
import com.example.psm.EditCitaActivity
import com.example.psm.EditarMascotaActivity
import com.example.psm.MascotasFragment
import com.example.psm.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CitasAdapter (private val citas: List<CitasModel>) : RecyclerView.Adapter<CitasAdapter.ViewHolder>(){
    var listener: CitasAdapter.CitaClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitasAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_citas,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cita = citas[position]

        // Configurar las vistas del ViewHolder con los datos de la mascota
        holder.nombreTextView.text = cita.NombreMascota
        holder.razaTextView.text = cita.RazaMascota
        holder.fechaTextView.text = cita.FechaCita
        holder.horaTextView.text = cita.HoraCita
        holder.doctorEditar.text = cita.NombreDoctor
        holder.idCitaEliminar.text = cita.IdCita.toString()
        // ... (configurar otras vistas)

        // Asociar el clic del botón con la posición de la mascota
        /*holder.botonEditar.setOnClickListener {
            // Lógica para editar la mascota
            Log.e("ID MAMALON EDITADO", cita.IdCita.toString())

            idCitaSingleton.currentCitaId = cita.IdCita

            val intentd = Intent(holder.itemView.context, EditCitaActivity::class.java)
            holder.itemView.context.startActivity(intentd)
        }*/

        holder.btnCitaEliminar.setOnClickListener {
            // Lógica para eliminar la mascota
            Log.e("ID MAMALON ELIMINADO", cita.IdCita.toString())

            val apiiN = RetrofitInstance.instance

            val call: Call<ApiRes> = apiiN.getApiDeleteCitas(cita.IdCita)

            Log.e("Prueba1:", "Despues de eliminar")

            call.enqueue(object : Callback<ApiRes> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    Log.e("Prueba2:", "Si jalooooooooo")
                    if (response.isSuccessful) {
                        val apiResponse: ApiRes? = response.body()
                        Log.e("exito", "Perfecto")

                        val intentd = Intent(holder.itemView.context, CitasFragment::class.java)
                        holder.itemView.context.startActivity(intentd)
                    } else{
                        Log.e("Error en la solicitud respuesta:", "No jalo")
                    }
                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud api:", t.message.toString())
                    val errorBody = call.request().body?.toString()
                    Log.e("Respuesta del servidor (error):", errorBody ?: "Error body is null")
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return citas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textNom)
        val razaTextView: TextView = itemView.findViewById(R.id.razaMascotaEdit)
        val fechaTextView: TextView = itemView.findViewById(R.id.textEspecie)
        val horaTextView: TextView = itemView.findViewById(R.id.textRaza)
        val doctorEditar: TextView = itemView.findViewById(R.id.textEdad)
        val idCitaEliminar: TextView = itemView.findViewById(R.id.textIDM)
        val btnCitaEliminar: AppCompatButton = itemView.findViewById(R.id.botonEliminarMascota)
    }

    interface CitaClickListener {
        fun onEditarMascotaClick(cita: CitasModel)
        fun onEliminarMascotaClick(cita: CitasModel)
    }
}