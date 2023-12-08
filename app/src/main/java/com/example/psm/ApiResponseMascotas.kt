package com.example.psm

data class ApiResponseMascotas(
    val idMascota: Int,
    val nombreMascota: String,
    val edad: Int,
    val raza: String,
    val activoMascota: Int,
    val idEspecie: Int,
    val nombreEspecie: String,
    val activoEspecie: Int,
    val idUsuario: Int,
    val idImagenesMascota: Int,
    val activoImagen: Int,
    val imagen: String
)
