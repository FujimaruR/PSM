package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class Citas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citas)

        val btninicio = findViewById<AppCompatButton>(R.id.botonInicio)

        val btnmascotas = findViewById<AppCompatButton>(R.id.botonMascotas)
        val btncitas = findViewById<AppCompatButton>(R.id.botonCitas)
        val btnperfil = findViewById<AppCompatButton>(R.id.botonPerfil)

        val btnagregarcita = findViewById<AppCompatButton>(R.id.boton5)
        val btneditarcita = findViewById<AppCompatButton>(R.id.boton1)
        val btneditarcitad = findViewById<AppCompatButton>(R.id.boton3)


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

        btnagregarcita.setOnClickListener {
            val intento = Intent(this, AddCita::class.java)
            startActivity(intento)
        }

        btneditarcita.setOnClickListener {
            val intentn = Intent(this, EditCitaActivity::class.java)
            startActivity(intentn)
        }

        btneditarcitad.setOnClickListener {
            val intentdi = Intent(this, EditCitaActivity::class.java)
            startActivity(intentdi)
        }
    }
}