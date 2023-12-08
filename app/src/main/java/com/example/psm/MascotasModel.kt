package com.example.psm

data class MascotasModel(
    val idMascota: Int,
    val nombre: String,
    val edad: Int,
    val raza: String,
    val activo: Int,
    val idEspecie: Int,
    val nomEspecie: String,
    val idUsuario: Int,
    val img1: ByteArray,
    val img2: ByteArray,
    val img3: ByteArray
)
