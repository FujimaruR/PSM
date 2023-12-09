package com.example.psm

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.example.psm.data.idMascotaSingleton
import com.example.psm.data.userSingleton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.Base64

class EditarMascotaActivity : AppCompatActivity() {

    private var especies: List<ApiResponseEspecies> = listOf()
    private var especieSeleccionada: ApiResponseEspecies? = null

    private val imagenes = mutableListOf<ImageView>()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_editar_mascota)

        val imageViewS = findViewById<ImageView>(R.id.fotoMascota)
        val imageView1 = findViewById<ImageView>(R.id.imagenIzquierda)
        val imageView2 = findViewById<ImageView>(R.id.imagenDerecha)

        imagenes.addAll(listOf(imageViewS, imageView1, imageView2))

        val btnreturn = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)


        var mnombre = findViewById<EditText>(R.id.editTextNombre)
        var mespecie = findViewById<Spinner>(R.id.spinnerMascot)
        var mraza = findViewById<EditText>(R.id.editTextRaza)
        var medad = findViewById<EditText>(R.id.editTextEdad)
        var ImageViews: ImageView = findViewById(R.id.imagenIzquierda)
        var ImageViews2: ImageView = findViewById(R.id.fotoMascota)
        var ImageViews3: ImageView = findViewById(R.id.imagenDerecha)


        val bitmap: Bitmap = BitmapFactory.decodeByteArray(idMascotaSingleton.currentImagen, 0, idMascotaSingleton.currentImagen!!.size)
        ImageViews.setImageBitmap(bitmap)

        val bitmap2: Bitmap = BitmapFactory.decodeByteArray(idMascotaSingleton.currentImagen, 0, idMascotaSingleton.currentImagen!!.size)
        ImageViews2.setImageBitmap(bitmap2)

        val bitmap3: Bitmap = BitmapFactory.decodeByteArray(idMascotaSingleton.currentImagen, 0, idMascotaSingleton.currentImagen!!.size)
        ImageViews3.setImageBitmap(bitmap3)


        val spinnerMascotas = findViewById<Spinner>(R.id.spinnerMascot)

        val selectedIndex = idMascotaSingleton.currentIdEspecie

        mnombre.setText(idMascotaSingleton.currentNameMascota)
        mraza.setText(idMascotaSingleton.currentRaza)
        medad.setText(idMascotaSingleton.currentEdad.toString())


        imageViewS.setOnClickListener { seleccionarImagenDeGaleria(1) }
        imageView1.setOnClickListener { seleccionarImagenDeGaleria(2) }
        imageView2.setOnClickListener { seleccionarImagenDeGaleria(3) }

        val btncancelar = findViewById<AppCompatButton>(R.id.botonArribaIzquierda)
        val btnconfirmar = findViewById<AppCompatButton>(R.id.GuardarMasc)

        val spinner: Spinner = findViewById(R.id.spinnerMascot)

        val apiInterface = RetrofitInstance.instance

        // Llamada a la API para obtener las especies
        apiInterface.getApiEspecies().enqueue(object: Callback<List<ApiResponseEspecies>> {
            override fun onResponse(call: Call<List<ApiResponseEspecies>>, response: Response<List<ApiResponseEspecies>>) {
                // Verificar que la respuesta sea exitosa y que contenga datos
                if (response.isSuccessful && response.body() != null) {
                    // Mapear la lista de respuesta a una lista de nombres de las especies
                    val especiesNombres = response.body()!!.map { it.nombre }

                    // Crear un ArrayAdapter usando la lista de nombres
                    val adapter = ArrayAdapter<String>(
                        this@EditarMascotaActivity,
                        android.R.layout.simple_spinner_item,
                        especiesNombres
                    )

                    // Asignar el ArrayAdapter al Spinner
                    spinner.adapter = adapter

                    especies = response.body()!!

                    Log.d("ESPECIES_SIZE", "Número de especies: ${especies.size}")
                } else {
                    Log.e("API_ERROR", "No se pudieron obtener las especies")
                }
            }

            override fun onFailure(call: Call<List<ApiResponseEspecies>>, t: Throwable) {
                // Manejar el error
                Log.e("API_ERROR", "No se pudieron obtener las especies", t)
            }
        })

        btncancelar.setOnClickListener {
        }

        btnconfirmar.setOnClickListener {
            var mnombre = findViewById<EditText>(R.id.editTextNombre)
            var mespecie = findViewById<Spinner>(R.id.spinnerMascot)
            var mraza = findViewById<EditText>(R.id.editTextRaza)
            var medad = findViewById<EditText>(R.id.editTextEdad)
            var ImageViews: ImageView = findViewById(R.id.fotoMascota)
            var imageView1: ImageView = findViewById(R.id.fotoMascota)
            var imageView2: ImageView = findViewById(R.id.fotoMascota)

            var nombrem = mnombre.text.toString()
            var enespecie = especieSeleccionada!!.idEspecie
            var razam = mraza.text.toString()
            var edadm = medad.text.toString()

            var enedad: Int = edadm.toInt()

            val drawable: Drawable = ImageViews.drawable

            val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap

            val byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val base64String = Base64.getEncoder().encodeToString(byteArray)
            val imgm:String = "data:image/png;base64," + base64String

            val drawable1: Drawable = imageView1.drawable

            val bitmap1: Bitmap = (drawable1 as BitmapDrawable).bitmap

            val byteArrayOutputStream1 = ByteArrayOutputStream()
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1)
            val byteArray1 = byteArrayOutputStream1.toByteArray()
            val base64String1 = Base64.getEncoder().encodeToString(byteArray1)
            val enimg2:String = "data:image/png;base64," + base64String1

            val drawable2: Drawable = imageView2.drawable

            val bitmap2: Bitmap = (drawable2 as BitmapDrawable).bitmap

            val byteArrayOutputStream2 = ByteArrayOutputStream()
            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2)
            val byteArray2 = byteArrayOutputStream2.toByteArray()
            val base64String2 = Base64.getEncoder().encodeToString(byteArray2)
            val enimg3:String = "data:image/png;base64," + base64String2

            val mascoteId = idMascotaSingleton.currentMascotaId

            val apiiN = RetrofitInstance.instance

            val call: Call<ApiRes> = apiiN.getApiEditMascotas(nombrem, enedad, razam, enespecie, mascoteId, imgm, enimg2, enimg3)

            val idEspecieSeleccionada = idMascotaSingleton.currentIdEspecie

            var index = -1
            for (i in especies.indices) {
                if (especies[i].idEspecie == idEspecieSeleccionada) {
                    index = i
                    break
                }
            }

            if (index != -1) {
                spinner.setSelection(index)
            }


            Log.e("Prueba1:", "Despues de registrar")

            call.enqueue(object : Callback<ApiRes> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onResponse(call: Call<ApiRes>, response: Response<ApiRes>) {
                    Log.e("Prueba2:", "Si jalooooooooo")
                    if (response.isSuccessful) {
                        val apiResponse: ApiRes? = response.body()
                        Log.e("exito", "Perfecto")

                        credenciales()
                    } else{
                        Log.e("Error en la solicitud respuesta:", "No jalo")
                        onError()
                    }
                }
                override fun onFailure(call: Call<ApiRes>, t: Throwable) {
                    Log.e("Error en la solicitud api:", t.message.toString())
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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (especies.isNotEmpty()) {
                    especieSeleccionada = especies[position]
                    Log.d("ID_ESPECIE", "ID de la especie seleccionada: ${especieSeleccionada?.idEspecie}")
                } else {
                    Log.d("ID_ESPECIE", "La lista de especies está vacía")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }
    }

    private fun seleccionarImagenDeGaleria(imagenNumero: Int) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, imagenNumero)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            imagenes.find { it.id == getImageViewId(requestCode) }?.setImageURI(imageUri)
        }
    }

    private fun getImageViewId(imageNumber: Int): Int {
        return when (imageNumber) {
            1 -> R.id.fotoMascota
            2 -> R.id.imagenIzquierda
            3 -> R.id.imagenDerecha
            else -> throw IllegalArgumentException("Número de imagen no válido: $imageNumber")
        }
    }

    fun credenciales(){
        if(userSingleton.currentUserName!!.isNotEmpty()){
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        } else{
            Toast.makeText(this, "Error al registrar Mascota", Toast.LENGTH_SHORT).show()
        }
    }

    fun onError(){
        Toast.makeText(this, "Error en la operacion", Toast.LENGTH_SHORT).show()
    }
}