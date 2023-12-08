package com.example.psm

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.util.Base64
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.psm.data.userSingleton

class PerfilFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)


        val btnperfilEdit = root.findViewById<AppCompatButton>(R.id.botonEditarPerfil)

        val nomcompleto = root.findViewById<TextView>(R.id.textView2)
        val fechaNacimiento = root.findViewById<TextView>(R.id.textView5)
        val telef = root.findViewById<TextView>(R.id.textView7)
        val sexo = root.findViewById<TextView>(R.id.textView9)
        val direccion = root.findViewById<TextView>(R.id.textView11)
        val correo = root.findViewById<TextView>(R.id.textView13)
        val imageUser: ImageView = root.findViewById(R.id.fotoUsuario)


        var sexostr: String = ""

        when (userSingleton.currentUserSexo) {
            0 -> sexostr = "Hombre"
            1 -> sexostr = "Mujer"
            else -> sexostr = "Otro"
        }


        nomcompleto.text = userSingleton.currentUserName
        fechaNacimiento.text = userSingleton.currentUserNacimiento
        telef.text = userSingleton.currentUserTelefono
        sexo.text = sexostr
        direccion.text = userSingleton.currentUserDireccion
        correo.text = userSingleton.currentUserCorreo

        val bitmap: ByteArray = Base64.decode(userSingleton.currentUserImg, Base64.DEFAULT)
        val bitmaperal = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        imageUser.setImageBitmap(bitmaperal)


        btnperfilEdit.setOnClickListener{
            val activityPerfil = Intent(this.requireContext(), editPerfil::class.java)
            startActivity(activityPerfil)
        }

        return root
    }


}