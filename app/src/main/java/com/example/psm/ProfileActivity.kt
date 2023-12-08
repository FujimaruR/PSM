package com.example.psm

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.userSingleton

class ProfileActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        val btninicio = findViewById<AppCompatButton>(R.id.botonInicio)

        val btnmascotas = findViewById<AppCompatButton>(R.id.botonMascotas)
        val btncitas = findViewById<AppCompatButton>(R.id.botonCitas)
        val btnperfil = findViewById<AppCompatButton>(R.id.botonPerfil)
        val btnperfilEdit = findViewById<AppCompatButton>(R.id.botonCargarImagenes)

        val nomcompleto = findViewById<TextView>(R.id.textView2)
        val fechaNacimiento = findViewById<TextView>(R.id.textView5)
        val telef = findViewById<TextView>(R.id.textView7)
        val sexo = findViewById<TextView>(R.id.textView9)
        val direccion = findViewById<TextView>(R.id.textView11)
        val correo = findViewById<TextView>(R.id.textView13)
        val imageUser: ImageView = findViewById(R.id.fotoMascota)

        var sexostr: String = ""

        when (userSingleton.currentUserSexo) {
            1 -> sexostr = "Hombre"
            2 -> sexostr = "Mujer"
            else -> sexostr = "Otro"
        }

        nomcompleto.text = userSingleton.currentUserName
        fechaNacimiento.text = userSingleton.currentUserNacimiento
        telef.text = userSingleton.currentUserTelefono
        sexo.text = sexostr
        direccion.text = userSingleton.currentUserDireccion
        correo.text = userSingleton.currentUserCorreo

        val bitmap: ByteArray = java.util.Base64.getDecoder().decode(userSingleton.currentUserImg)
        val bitmaperal = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        imageUser.setImageBitmap(bitmaperal)

        btninicio.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }



        btnmascotas.setOnClickListener {
            //  val intentt = Intent(this, MascotasActivity::class.java)
            //  startActivity(intentt)
        }

        btncitas.setOnClickListener {
            val intentc = Intent(this, CitasFragment::class.java)
            startActivity(intentc)
        }

        btnperfil.setOnClickListener {
            val intents = Intent(this, ProfileActivity::class.java)
            startActivity(intents)
        }

        btnperfilEdit.setOnClickListener{
            val intentsi = Intent(this, editPerfil::class.java)
            startActivity(intentsi)
        }
    }
}