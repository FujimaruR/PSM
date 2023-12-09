package com.example.psm
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {
    //Login
    @FormUrlEncoded
    @POST("InicioSesion.php")
    fun getApiResponseUnique(@Field("email") correo: String, @Field("password") passw: String): Call<UsersResponse>

    // Registro de Usuarios
    @FormUrlEncoded
    @POST("Registro.php")
    fun getApiResponseInsert(@Field("rol") rol: Int, @Field("sexo") sexo: Int, @Field("nombre") nombre: String, @Field("telefono") telefono: String, @Field("passw") passw:String, @Field("correo") correo:String,@Field("imagen") imagen:String,@Field("fechaNacimiento") fechaNacimiento:String,@Field("direccion") direccion:String ): Call<ApiRes>

    // Edicion de Usuarios
    @FormUrlEncoded
    @POST("ModificarDatosUsuario.php")
    fun getApiEditResponse(@Field("sexo") sexo: Int, @Field("idUsuario")idUsuario:Int,@Field("nombre") nombre: String, @Field("telefono") telefono: String, @Field("passw") passw:String, @Field("correo") correo:String,@Field("imagen") imagen:String,@Field("fechaNacimiento") fechaNacimiento:String,@Field("direccion") direccion:String ): Call<ApiRes>

    //Pagina offline
    @FormUrlEncoded
    @POST("RegistroMascotasOffline.php")
    fun getApiInsertOffline(@Field("nombre") nombre:String?=null, @Field("edad") edad: Int?=null, @Field("raza") raza: String?=null, @Field("activo") activo: Int?=null, @Field("idEspecie") idEspecie: Int?=null, @Field("idUsuario") idUsuario: Int?=null, @Field("idMascotaD") idMascotaD: Int?=null, @Field("image") image: String?=null, @Field("image2") image2: String?=null, @Field("image3") image3: String?=null): Call<ApiRes>

    //login Admin
    @FormUrlEncoded
    @POST("InicioSesionAdmin.php")
    fun getApiResponseUniqueAdmin(@Field("email") correo: String, @Field("password") passw: String): Call<ApiRes>

    // Insert Especie
    @FormUrlEncoded
    @POST("InsertEspecie.php")
    fun getApiResponseInsertEspecie(@Field("especie") especie: String): Call<ApiRes>

    @FormUrlEncoded
    @POST("InsertDoctor.php")
    fun getApiResponseInsertDoctor(@Field("nombre") nombre: String,@Field("activo") activo: Int?): Call<ApiRes>

    @FormUrlEncoded
    @POST("EditarEspecie.php")
    fun getApiModificarEspecie(@Field("especie") especie: String, @Field("idEspecie") idEspecie: Int?): Call<ApiRes>

    @FormUrlEncoded
    @POST("UpdateDoctor.php")
    fun getApiModificarDoctor(@Field("nombre") nombre: String, @Field("activo") activo: Int? ,@Field("idDoctor") idDoctor: Int?): Call<ApiRes>

    @FormUrlEncoded
    @POST("EliminarEspecie.php")
    fun getApiEliminarESPECIE(@Field("idEspecie") idEspecie: Int?): Call<ApiRes>

    @FormUrlEncoded
    @POST("DeleteDoctor.php")
    fun getApiEliminarDoctor(@Field("idDoctor") idEspecie: Int?): Call<ApiRes>


    @POST("GetEspecies.php")
    fun getApiEspecies(): Call<List<ApiResponseEspecies>>

    @POST("ObtenerDoctores.php")
    fun getApiDoctores(): Call<List<ApiResponseDoctores>>

    @FormUrlEncoded
    @POST("GetMascotas.php")
    fun getApiMascotas(@Field("idUsuario") idUsuario: Int?=null): Call<List<ApiResponseMascotas>>

    //registro mascotas
    @FormUrlEncoded
    @POST("RegistroMascotas.php")
    fun getApiInsertMascotas(@Field("nombre") nombre:String?=null, @Field("edad") edad: Int?=null, @Field("raza") raza: String?=null, @Field("activo") activo: Int?=null, @Field("idEspecie") idEspecie: Int?=null, @Field("idUsuario") idUsuario: Int?=null, @Field("imagen1") image: String?=null, @Field("imagen2") image2: String?=null, @Field("imagen3") image3: String?=null): Call<ApiRes>

    @FormUrlEncoded
    @POST("ModificarMascota.php")
    fun getApiEditMascotas(@Field("nombre") nombre:String?=null, @Field("edad") edad: Int?=null, @Field("raza") raza: String?=null, @Field("idEspecie") idEspecie: Int?=null, @Field("idMascota") idMascota: Int?=null, @Field("imagen1") image: String?=null, @Field("imagen2") image2: String?=null, @Field("imagen3") image3: String?=null): Call<ApiRes>

    @FormUrlEncoded
    @POST("DeleteMascota.php")
    fun getApiDeleteMascotas(@Field("idMascota") idMascota: Int?=null): Call<ApiRes>


    @FormUrlEncoded
    @POST("InsertCita.php")
    fun getApiInsertCita(@Field("hora") hora:String?=null, @Field("idMascota") idMascota: Int?=null, @Field("idUsuario") idUsuario: Int?=null, @Field("idDoctor") idDoctor: Int?=null, @Field("activo") activo: Int?=null, @Field("fecha")
    fecha: String?=null): Call<ApiRes>

    @FormUrlEncoded
    @POST("BajaCita.php")
    fun getApiDeleteCitas(@Field("idCita") idCita: Int?=null): Call<ApiRes>

    @FormUrlEncoded
    @POST("ObtenerCitas.php")
    fun getApiCitas(@Field("idUsuario") idUsuario: Int?=null): Call<List<ApiResponseCitas>>


}