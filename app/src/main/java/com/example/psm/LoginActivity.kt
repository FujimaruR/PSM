package com.example.psm

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.DataDBlite
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Base64

class LoginActivity : AppCompatActivity() {

    private lateinit var datadblite: DataDBlite
    var currentIndex = 1
    var image: String?=null
    var image2: String?=null
    var image3: String?=null
    var lastid: Int ?= null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)
        datadblite = (DataDBlite(this))

        val btnregister = findViewById<AppCompatButton>(R.id.buttonInicio)
        val btnreturn = findViewById<AppCompatButton>(R.id.buttonRegresar)


        btnreturn.setOnClickListener {
            val intent = Intent(this, InitActivity::class.java)
            startActivity(intent)
        }



            btnregister.setOnClickListener {
                datadblite = (DataDBlite(this))
                var bemail = findViewById<EditText>(R.id.TextMailEd)
                var bcontra = findViewById<EditText>(R.id.TextPassworded)

                var email = bemail.text.toString()
                var password = bcontra.text.toString()

                val apiInterface = RetrofitInstance.instance
                val call: Call<UsersResponse> = apiInterface.getApiResponseUnique(email,password)
                Log.e("exito", "Perfecto")
                call.enqueue(object : Callback<UsersResponse> {
                    override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                        if (response.isSuccessful) {
                            Log.e("exitoDos", "Perfecto")
                            val apiResponse: UsersResponse? = response.body()
                            apiResponse?.let { Log.e("Exito", it.nombre) }
                            userSingleton.currentUserId = apiResponse?.idUsuario
                            userSingleton.currentUserSexo = apiResponse?.sexo
                            userSingleton.currentUserName = apiResponse!!.nombre
                            userSingleton.currentUserTelefono = apiResponse.telefono
                            userSingleton.currentUserPassw = apiResponse.passw
                            userSingleton.currentUserCorreo = apiResponse.correo
                            userSingleton.currentUserImg = apiResponse.imagen
                            val imageDataBytes = java.util.Base64.getDecoder().decode(userSingleton.currentUserImg!!.substringAfter(','))
                            userSingleton.currentUserNacimiento = apiResponse.fechaNacimiento
                            userSingleton.currentUserDireccion = apiResponse.direccion
                            var usersAdd = UserModel(
                                idUsuario = userSingleton.currentUserId,
                                sexo = userSingleton.currentUserSexo,
                                nombre = userSingleton.currentUserName,
                                telefono = userSingleton.currentUserTelefono,
                                passw = userSingleton.currentUserPassw,
                                correo = userSingleton.currentUserCorreo,
                                imagen = imageDataBytes,
                                fechaNacimiento = userSingleton.currentUserNacimiento,
                                direccion = userSingleton.currentUserDireccion
                            )
                            datadblite.insertUsers(usersAdd)

                            val roles = apiResponse.rol
                            if(roles == 0){
                                Log.e("normal", "Perfecto")
                                credenciales()
                            } else{
                                Log.e("admin", "Perfecto")
                                credencialesD()
                            }
                        } else {
                            val apiInterface = RetrofitInstance.instance
                            val call: Call<ApiRes> = apiInterface.getApiResponseUniqueAdmin(email,password)

                            call.enqueue(object : Callback<ApiRes> {
                                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                                    if (response.isSuccessful) {
                                        if (response.code() == 200) {
                                            // Inicio de sesión exitoso, realizar acciones necesarias
                                        } else if (response.code() == 401) {
                                            // Inicio de sesión fallido, mostrar mensaje de error
                                        }
                                    } else {
                                        // Manejar errores de comunicación con el servidor
                                    }

                                }
                                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                                    Log.e("Error en la solicitud:", t.message.toString())

                                }
                            })

                        }
                    }
                    override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                        Log.e("Error en la solicitud:", t.message.toString())
                        onError()
                    }
                })

            }



    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                // Puede agregar más transportes según sus necesidades
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnectedOrConnecting
        }
    }

    fun credenciales(){
        if(userSingleton.currentUserName!!.isNotEmpty()){
            val intentd = Intent(this, InicioActivity::class.java)
            startActivity(intentd)
        } else{
            Toast.makeText(this, "Error al registrar usuario credencial", Toast.LENGTH_SHORT).show()
        }
    }

    fun credencialesD(){
        if(userSingleton.currentUserName!!.isNotEmpty()){
            val intentt = Intent(this, adminActivity::class.java)
            startActivity(intentt)
        } else{
            Toast.makeText(this, "Error al registrar usuario credencial", Toast.LENGTH_SHORT).show()
        }
    }

    fun credencialesAdmin(){
            val intentcu = Intent(this, adminActivity::class.java)
            startActivity(intentcu)
    }

    fun onError(){
        Toast.makeText(this, "Error al registrar usuario error", Toast.LENGTH_SHORT).show()
    }
}