package com.example.psm.data

import android.content.Context
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
import com.example.psm.EditarMascotaActivity
import com.example.psm.MascotasFragment
import com.example.psm.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MascotaAdapter(private val mascotas: List<MascotasModel>) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>(){

    var listener: MascotaClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_mascotas,parent,false)
        return ViewHolder(v)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mascota = mascotas[position]

        // Configurar las vistas del ViewHolder con los datos de la mascota
        holder.nombreTextView.text = mascota.nombre
        holder.especieTextView.text = mascota.nomEspecie
        holder.razaTextView.text = mascota.raza
        holder.edadTextView.text = mascota.edad.toString()
        holder.idTextView.text = mascota.idMascota.toString()
        // ... (configurar otras vistas)

        val bitmap = BitmapFactory.decodeByteArray(mascota.img1, 0, mascota.img1.size)
        holder.imagenImageView.setImageBitmap(bitmap)

        // Asociar el clic del botón con la posición de la mascota
        holder.botonEditar.setOnClickListener {
            // Lógica para editar la mascota
            Log.e("ID MAMALON EDITADO", mascota.idMascota.toString())

            idMascotaSingleton.currentMascotaId = mascota.idMascota

            val intentd = Intent(holder.itemView.context, EditarMascotaActivity::class.java)
            holder.itemView.context.startActivity(intentd)
        }

        holder.botonEliminar.setOnClickListener {
            // Lógica para eliminar la mascota
            Log.e("ID MAMALON ELIMINADO", mascota.idMascota.toString())

            val apiiN = RetrofitInstance.instance

            val call: Call<ApiRes> = apiiN.getApiDeleteMascotas(mascota.idMascota)

            Log.e("Prueba1:", "Despues de eliminar")

            call.enqueue(object : Callback<ApiRes> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    Log.e("Prueba2:", "Si jalooooooooo")
                    if (response.isSuccessful) {
                        val apiResponse: ApiRes? = response.body()
                        Log.e("exito", "Perfecto")

                        val intentd = Intent(holder.itemView.context, MascotasFragment::class.java)
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
        return mascotas.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.textNom)
        val especieTextView: TextView = itemView.findViewById(R.id.textEspecie)
        val razaTextView: TextView = itemView.findViewById(R.id.textRaza)
        val edadTextView: TextView = itemView.findViewById(R.id.textEdad)
        val idTextView: TextView = itemView.findViewById(R.id.textIDM)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageMascota)
        val botonEditar: AppCompatButton = itemView.findViewById(R.id.botonEditarMascota)
        val botonEliminar: AppCompatButton = itemView.findViewById(R.id.botonEliminarMascota)
    }

    interface MascotaClickListener {
        fun onEditarMascotaClick(mascota: MascotasModel)
        fun onEliminarMascotaClick(mascota: MascotasModel)
    }

}


