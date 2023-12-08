package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class EditarMascotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        val btncancelar = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)
        val btnconfirmar = findViewById<AppCompatButton>(R.id.GuardarMasc)

        btncancelar.setOnClickListener {
        }

        btnconfirmar.setOnClickListener {
        }
    }
}