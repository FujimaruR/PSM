package com.example.psm

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import java.io.ByteArrayOutputStream
import java.util.Base64

class EditarMascotaActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_mascota)

        val btncancelar = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)
        val btnconfirmar = findViewById<AppCompatButton>(R.id.GuardarMasc)

        btncancelar.setOnClickListener {
        }

        btnconfirmar.setOnClickListener {
            var mnombre = findViewById<EditText>(R.id.editTextNombre)
            var mespecie = findViewById<Spinner>(R.id.spinnerMascot)
            var mraza = findViewById<EditText>(R.id.editTextRaza)
            var medad = findViewById<EditText>(R.id.editTextEdad)
            var mpeso = findViewById<EditText>(R.id.editTextPeso)
            var ImageViews: ImageView = findViewById(R.id.fotoMascota)

            var nombrem = mnombre.text.toString()
            var razam = mraza.text.toString()
            var edadm = medad.text.toString()
            var pesom = mpeso.text.toString()

            val drawable: Drawable = ImageViews.drawable

            val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64String = Base64.getEncoder().encodeToString(byteArray)
            val imgm:String = "data:image/png;base64," + base64String
        }
    }
}