package com.example.psm

data class UsersResponse(
    val idUsuario: Int,
    val sexo: Int,
    val nombre: String,
    val telefono: String,
    val passw: String,
    val correo: String,
    val imagen: String,
    val fechaNacimiento: String,
    val direccion: String
)
