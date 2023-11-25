package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class MascotasActivity : AppCompatActivity() {
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

        btninicio.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }



        btnmascotas.setOnClickListener {
            val intentt = Intent(this, MascotasActivity::class.java)
            startActivity(intentt)
        }

        btncitas.setOnClickListener {
            val intentc = Intent(this, Citas::class.java)
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