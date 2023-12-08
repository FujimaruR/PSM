package com.example.psm.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.psm.R
import com.example.psm.MascotasModel
import android.graphics.BitmapFactory

class MascotaAdapter(private val mascotas: List<MascotasModel>) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>(){

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
    }

    override fun getItemCount(): Int {
        return mascotas.size
    }

    inner class ViewHolder (itemView: View) :RecyclerView.ViewHolder(itemView){
        val nombreTextView: TextView = itemView.findViewById(R.id.textNom)
        val especieTextView: TextView = itemView.findViewById(R.id.textEspecie)
        val razaTextView: TextView = itemView.findViewById(R.id.textRaza)
        val edadTextView: TextView = itemView.findViewById(R.id.textEdad)
        val idTextView: TextView = itemView.findViewById(R.id.textIDM)
        val imagenImageView: ImageView = itemView.findViewById(R.id.imageMascota)
    }
}