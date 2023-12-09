package com.example.psm

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.DataDBlite
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.Float
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Calendar
import java.util.Locale
import kotlin.Exception
import kotlin.String
import kotlin.Throwable
import kotlin.let
import kotlin.toString

class editPerfil : AppCompatActivity() {

    private lateinit var datadblite: DataDBlite
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_perfil)
        datadblite = (DataDBlite(this))


        val btnreturn = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)

        // CARGA DE DATA DEL USUARIO

        val nomcompleto = findViewById<TextView>(R.id.editTextUsername)
        val fechaNacimiento = findViewById<TextView>(R.id.editDTP)
        val telef = findViewById<TextView>(R.id.editTextPhone)
        val direccion = findViewById<TextView>(R.id.editTextAddress)
        val contrasena = findViewById<TextView>(R.id.editTextContra)
        val correo = findViewById<TextView>(R.id.editTextEmail)
        val imageUser: ImageView = findViewById(R.id.profile_picture)

        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val targetFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val date = originalFormat.parse(userSingleton.currentUserNacimiento) // La fecha que quieres convertir
        val formattedDate = targetFormat.format(date)

        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)

        val selectedIndex = when (userSingleton.currentUserSexo) {
            0 -> 0  // Hombre
            1 -> 1  // Mujer
            else -> 2  // Otro
        }
        spinnerGender.setSelection(selectedIndex)


        nomcompleto.text = userSingleton.currentUserName
        fechaNacimiento.text = formattedDate
        telef.text = userSingleton.currentUserTelefono
        direccion.text = userSingleton.currentUserDireccion
        correo.text = userSingleton.currentUserCorreo
        contrasena.text = userSingleton.currentUserPassw

        val bitmap: ByteArray = android.util.Base64.decode(userSingleton.currentUserImg, android.util.Base64.DEFAULT)
        val bitmaperal = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.size)
        imageUser.setImageBitmap(bitmaperal)


        supportActionBar?.hide()

        val editTextFecha = findViewById<EditText>(R.id.editDTP)
        val buttonSelectImage = findViewById<AppCompatButton>(R.id.select_image_button)
        val imageViewS = findViewById<ImageView>(R.id.profile_picture)
        try {
            resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("RegisterActivity", "Botón de selección de imagen clickeado")
                    val selectedImageUri: Uri? = result.data?.data
                    selectedImageUri?.let {
                        Log.d("RegisterActivity", "Botón de selección de imagen c")
                        imageViewS.setImageURI(it)

                        val maxWidth = resources.displayMetrics.widthPixels
                        val maxHeight = resources.displayMetrics.heightPixels

                        val drawable: Drawable = imageViewS.drawable
                        val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

                        val scaleFactor = Float.min(
                            maxWidth.toFloat() / bitmap.width.toFloat(),
                            maxHeight.toFloat() / bitmap.height.toFloat()
                        )

                        // Escala el bitmap
                        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scaleFactor).toInt(), (bitmap.height * scaleFactor).toInt(), true)

                        imageViewS.setImageBitmap(scaledBitmap)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        val btnregister = findViewById<AppCompatButton>(R.id.buttonRegister)
        val imageView = findViewById<ImageView>(R.id.profile_picture)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        editTextFecha.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    // El usuario ha seleccionado una fecha
                    val fechaSeleccionada = "$dayOfMonth/${month + 1}/$year"
                    editTextFecha.setText(fechaSeleccionada)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        buttonSelectImage.setOnClickListener {
            openFileDialog()
        }

        btnregister.setOnClickListener {
            var bnombre = findViewById<EditText>(R.id.editTextUsername)
            var bemail = findViewById<EditText>(R.id.editTextEmail)
            var bcontra = findViewById<EditText>(R.id.editTextContra)
            var bfecha = findViewById<EditText>(R.id.editDTP)
            var bgenero = findViewById<Spinner>(R.id.spinnerGender)
            var btelefono = findViewById<EditText>(R.id.editTextPhone)
            var bdireccion = findViewById<EditText>(R.id.editTextAddress)
            var ImageViews:ImageView = findViewById(R.id.profile_picture)

            var ennombre = bnombre.text.toString()
            var enemail = bemail.text.toString()
            var encontra = bcontra.text.toString()
            var entelefono = btelefono.text.toString()
            var endireccion = bdireccion.text.toString()

            val enfechaText = bfecha.text.toString()
            Log.d("Cadena de Fecha", enfechaText)

            val drawable: Drawable = imageView.drawable

            val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64String = Base64.getEncoder().encodeToString(byteArray)
            val enimg:String = "data:image/png;base64," + base64String

            val indiceSeleccionado = bgenero.selectedItemPosition

            val valorSeleccionado = when (indiceSeleccionado) {
                0 -> false // Hombre
                1 -> true // Mujer
                else -> false // Maneja cualquier otro caso si es necesario
            }

            //val generoValue = bgenero.selectedItemPosition Supongamos que 0 es masculino y 1 es femenino
            val engenero = valorSeleccionado

            val sexoInt = if (engenero) 0 else 1

            val calendar = Calendar.getInstance()
            val currentDate = calendar.time

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val currentDateString = dateFormat.format(calendar.getTime())

            val idus = userSingleton.currentUserId ?: 0

            val apiiN = RetrofitInstance.instance

            val call: Call<ApiRes> = apiiN.getApiEditResponse(sexoInt, idus, ennombre, entelefono, encontra, enemail, enimg, enfechaText, endireccion)

            call.enqueue(object : Callback<ApiRes> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    Log.e("Hola:", "Si jalooooooooo")
                    if (response.isSuccessful) {
                        val apiResponse: ApiRes? = response.body()
                        Log.e("exito", "Perfecto")
                        val apiiN = RetrofitInstance.instance
                        val call: Call<UsersResponse> = apiiN.getApiResponseUnique(enemail,encontra)
                        call.enqueue(object : Callback<UsersResponse> {
                            override fun onResponse(call: Call<UsersResponse>, response: Response<UsersResponse>) {
                                if (response.isSuccessful) {
                                    val apiResponse: UsersResponse? = response.body()
                                    apiResponse?.let { Log.e("Exito", it.nombre) }
                                    userSingleton.currentUserId = apiResponse?.idUsuario
                                    userSingleton.currentUserSexo = apiResponse?.sexo
                                    userSingleton.currentUserName = apiResponse!!.nombre
                                    userSingleton.currentUserTelefono = apiResponse.telefono
                                    userSingleton.currentUserPassw = apiResponse.passw
                                    userSingleton.currentUserCorreo = apiResponse.correo
                                    userSingleton.currentUserImg = apiResponse.imagen
                                    val imageDataBytes = java.util.Base64.getDecoder().decode(
                                        userSingleton.currentUserImg!!.substringAfter(','))
                                    userSingleton.currentUserNacimiento = apiResponse.fechaNacimiento
                                    userSingleton.currentUserDireccion = apiResponse.direccion

                                    var usersAdd = UserModel(
                                        sexo = if (engenero) 0 else 1,
                                        nombre = ennombre,
                                        telefono = entelefono,
                                        passw = encontra,
                                        correo = enemail,
                                        imagen = imageDataBytes,
                                        fechaNacimiento = enfechaText,
                                        direccion = endireccion
                                    )
                                    datadblite.updateUser(usersAdd)

                                    credenciales()
                                } else {
                                    Log.e("Cosas Error: ", "No jaló")

                                }
                            }
                            override fun onFailure(call: Call<UsersResponse>, t: Throwable) {
                                Log.e("Error en la solicitud:", t.message.toString())

                            }
                        })
                    } else{
                        Log.e("Error en la solicitud:", "No jalo")
                        onError()
                    }
                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud:", t.message.toString())
                    onError()
                    val errorBody = call.request().body?.toString()
                    Log.e("Respuesta del servidor (error):", errorBody ?: "Error body is null")
                }
            })

        }

        btnreturn.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }


    }



    fun openFileDialog() {
        try {
            Log.d("EdirUserActivity", "Abriendo el diálogo de selección de imagen")
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun credenciales(){
        Log.d("Entre a credenciales", "He entrado a credenciales.")
        if(userSingleton.currentUserName!!.isNotEmpty()){
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this, "Error al editar usuario credencial", Toast.LENGTH_SHORT).show()
        }
    }

    fun onError(){
        Toast.makeText(this, "Error al editar usuario", Toast.LENGTH_SHORT).show()
    }
}