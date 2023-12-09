package com.example.psm

data class ApiResponseMascota(
    val idMascota: Int,
    val nombreMascota: String,
    val edad: Int,
    val raza: String,
    val activoMascota: Int,
    val idEspecie: Int,
    val nombreEspecie: String,
    val imagen: String
)
