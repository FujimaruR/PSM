package com.example.psm

data class MascotasOffline(
    val nombre: String?,
    val edad: Int?,
    val raza: String?,
    val activo: Int?,
    val idEspecie: Int,
    val idUsuario: Int,
    val idMascotaD: Int,
    val MascotImg: List<ByteArray>? = null
)
