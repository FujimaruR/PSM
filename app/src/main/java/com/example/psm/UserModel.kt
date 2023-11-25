package com.example.psm

import java.util.Random

class UserModel(
    var idUsuario: Int? = null,
    var sexo: Int? = null,
    var nombre: String? = "",
    var telefono: String? = "",
    var passw: String? = "",
    var correo: String? = "",
    var imagen: ByteArray? = null,
    var fechaNacimiento: String? = "",
    var direccion: String? = "",
) {
    companion object{
        fun getAutoId():Int{
            val random = Random()
            return random.nextInt(100)
        }
    }

}