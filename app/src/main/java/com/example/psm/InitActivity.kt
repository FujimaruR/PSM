package com.example.psm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton

class InitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)

        val btnlogin = findViewById<AppCompatButton>(R.id.boton1)
        val btnregister = findViewById<AppCompatButton>(R.id.boton2)

        btnlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnregister.setOnClickListener {
            val intentd = Intent(this, RegisterActivity::class.java)
            startActivity(intentd)
        }
    }
}