package com.example.psm

data class UserOffline(
    var idUsuario: Int? = null,
    var sexo: Int? = null,
    var nombre: String? = null,
    var telefono: String? = null,
    var passw: String? = null,
    var correo: String? = null,
    var imagen: ByteArray? = null,
    var fechaNacimiento: String? = null,
    var direccion: String? = null
)
