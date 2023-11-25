package com.example.psm

data class Users(
    val userId: Int?,
    val userSexo: Int?,
    val userName: String?,
    val userTelefono: String?,
    val userPassw: String?,
    val userCorreo: String?,
    val imageData: ByteArray?,
    val userNacimiento: String?,
    val userDireccion: String?
)
