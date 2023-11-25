package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class adminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        val btnDoctores = findViewById<AppCompatButton>(R.id.btnModDoctores)

        btnDoctores.setOnClickListener {
            val intent = Intent(this, DoctoresActivity::class.java)
            startActivity(intent)
        }


        val btnMascota = findViewById<AppCompatButton>(R.id.btnModMascota)

        btnMascota.setOnClickListener {
            val intent = Intent(this, MascotasAdminActivity::class.java)
            startActivity(intent)
        }




    }



}