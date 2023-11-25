package com.example.psm.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.psm.MascotasOffline
import com.example.psm.MascotasOfflineList
import com.example.psm.UserModel
import com.example.psm.UserOffline

class DataDBlite(var context: Context): SQLiteOpenHelper(context, setDBlite.DB_Name, null, setDBlite.DB_Version) {

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createUsersTable: String = "CREATE TABLE " + setDBlite.tblUsuarios.Table_Name + "(" +
                    setDBlite.tblUsuarios.IDUSUARIO + " INTEGER PRIMARY KEY, " +
                    setDBlite.tblUsuarios.SEXO + " INTEGER, " +
                    setDBlite.tblUsuarios.NOMBRE + " VARCHAR(10), " +
                    setDBlite.tblUsuarios.TELEFONO + " VARCHAR(50), " +
                    setDBlite.tblUsuarios.PASSW + " VARCHAR(50), " +
                    setDBlite.tblUsuarios.CORREO + " VARCHAR(50), " +
                    setDBlite.tblUsuarios.IMAGEN + " LONGBLOB, " +
                    setDBlite.tblUsuarios.FECHANACIMIENTO + " DATE, " +
                    setDBlite.tblUsuarios.DIRECCION + " VARCHAR(50)) "

            db?.execSQL(createUsersTable)

            val createMascotasTable: String = "CREATE TABLE " + setDBlite.tblMascotas.Table_Name + "(" +
                    setDBlite.tblMascotas.COL_idMascota + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    setDBlite.tblMascotas.COL_nombre + " VARCHAR(100), " +
                    setDBlite.tblMascotas.COL_edad + " INTEGER, " +
                    setDBlite.tblMascotas.COL_raza + " VARCHAR(100), " +
                    setDBlite.tblMascotas.COL_activo + " INTEGER, " +
                    setDBlite.tblMascotas.COL_idEspecie + " INTEGER, " +
                    setDBlite.tblMascotas.COL_idUsuario + " INTEGER) "

            db?.execSQL(createMascotasTable)

            val createImgMascotasTable: String = "CREATE TABLE " + setDBlite.tblImagenesMascota.Table_Name + "(" +
                    setDBlite.tblImagenesMascota.COL_idImagenesMascota + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    setDBlite.tblImagenesMascota.COL_idMascota + " INTEGER, " +
                    setDBlite.tblImagenesMascota.COL_activo + " INTEGER, " +
                    setDBlite.tblImagenesMascota.COL_imagen + " LONGBLOB, " +
                    setDBlite.tblImagenesMascota.COL_imagen2 + " LONGBLOB, " +
                    setDBlite.tblImagenesMascota.COL_imagen3 + " LONGBLOB) "

            db?.execSQL(createImgMascotasTable)

            val createCitasTable: String = "CREATE TABLE " + setDBlite.tblCitas.Table_Name + "(" +
                    setDBlite.tblCitas.COL_idCita + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    setDBlite.tblCitas.COL_Hora + " DATE, " +
                    setDBlite.tblCitas.COL_idMascota + " INTEGER, " +
                    setDBlite.tblCitas.COL_idUsuario + " INTEGER, " +
                    setDBlite.tblCitas.COL_idDoctor + " INTEGER, " +
                    setDBlite.tblCitas.COL_tipo + " VARCHAR(100), " +
                    setDBlite.tblCitas.COL_activo + " INTEGER, " +
                    setDBlite.tblCitas.COL_activoRecurrencia + " INTEGER) "

            db?.execSQL(createCitasTable)

            val createDoctoresTable: String = "CREATE TABLE " + setDBlite.tblDoctores.Table_Name + "(" +
                    setDBlite.tblDoctores.COL_idDoctor + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    setDBlite.tblDoctores.COL_nombre + " VARCHAR(100), " +
                    setDBlite.tblDoctores.COL_activo + " INTEGER, " +
                    setDBlite.tblDoctores.COL_horarioEntrada + " DATE, " +
                    setDBlite.tblDoctores.COL_horarioSalida + " DATE) "

            db?.execSQL(createDoctoresTable)

            val createEspeciesTable: String = "CREATE TABLE " + setDBlite.tblEspecies.Table_Name + "(" +
                    setDBlite.tblEspecies.COL_idEspecie + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    setDBlite.tblEspecies.COL_nombre + " VARCHAR(100), " +
                    setDBlite.tblEspecies.COL_activo + " INTEGER) "

            db?.execSQL(createEspeciesTable)

        } catch (e: Exception) {
            Log.e("DataDBHelper", "Error al crear las tablas del usuarios", e)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insertUsers(std: UserModel):Boolean {
        val dataBase:SQLiteDatabase = this.writableDatabase
        var boolResult:Boolean =  true

        val contentValues = ContentValues()
        contentValues.put(setDBlite.tblUsuarios.IDUSUARIO, std.idUsuario)
        contentValues.put(setDBlite.tblUsuarios.SEXO, std.sexo)
        contentValues.put(setDBlite.tblUsuarios.NOMBRE, std.nombre)
        contentValues.put(setDBlite.tblUsuarios.TELEFONO, std.telefono)
        contentValues.put(setDBlite.tblUsuarios.PASSW, std.passw)
        contentValues.put(setDBlite.tblUsuarios.CORREO, std.correo)
        contentValues.put(setDBlite.tblUsuarios.IMAGEN, std.imagen)
        contentValues.put(setDBlite.tblUsuarios.FECHANACIMIENTO, std.fechaNacimiento)
        contentValues.put(setDBlite.tblUsuarios.DIRECCION, std.direccion)

        try{
            val result =  dataBase.insert(setDBlite.tblUsuarios.Table_Name, null, contentValues)
            if (result == (0).toLong()) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception){
            Log.e("Execption", e.toString())
            boolResult =  false
        }

        dataBase.close()
        return boolResult
    }

    fun updateUser(user: UserModel): Boolean {
        val dataBase: SQLiteDatabase = this.writableDatabase
        val values: ContentValues = ContentValues()
        var boolResult: Boolean = true

        values.put(setDBlite.tblUsuarios.SEXO, user.sexo)
        values.put(setDBlite.tblUsuarios.NOMBRE, user.nombre)
        values.put(setDBlite.tblUsuarios.TELEFONO, user.telefono)
        values.put(setDBlite.tblUsuarios.PASSW, user.passw)
        values.put(setDBlite.tblUsuarios.CORREO, user.correo)
        values.put(setDBlite.tblUsuarios.IMAGEN, user.imagen)
        values.put(setDBlite.tblUsuarios.FECHANACIMIENTO, user.fechaNacimiento)
        values.put(setDBlite.tblUsuarios.DIRECCION, user.direccion)

        val whereClause = "${setDBlite.tblUsuarios.IDUSUARIO} = ?"
        val whereArgs = arrayOf(user.idUsuario.toString())

        try {
            val result = dataBase.update(setDBlite.tblUsuarios.Table_Name, values, whereClause, whereArgs)
            if (result == 0) {
                Toast.makeText(this.context, "Failed", Toast.LENGTH_SHORT).show()
                boolResult = false
            } else {
                Toast.makeText(this.context, "Success", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Log.e("Exception", e.toString())
            boolResult = false
        }

        dataBase.close()
        return boolResult
    }

    fun showAnimalsOffline(context: Context) {
        val dataBase = this.readableDatabase
        val query = "SELECT * FROM ${setDBlite.tblMascotas.Table_Name}"
        val cursor = dataBase.rawQuery(query, null)
        var idMascota: Int? = null
        var nombre: String? = null
        var edad: Int? = null
        var raza: String? = null
        var activo: Int? = null
        var idEspecie: Int? = null
        var idUsuario: Int? = null
        var idMascotaD: Int? = null
        var img1:ByteArray?=null
        var img2:ByteArray?=null
        var img3:ByteArray?=null

        val mascotaDetails = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                idMascota = cursor.getInt(cursor.getColumnIndex(setDBlite.tblMascotas.COL_idMascota))
                nombre = cursor.getString(cursor.getColumnIndex(setDBlite.tblMascotas.COL_nombre))
                edad = cursor.getInt(cursor.getColumnIndex(setDBlite.tblMascotas.COL_edad))
                raza = cursor.getString(cursor.getColumnIndex(setDBlite.tblMascotas.COL_raza))
                activo = cursor.getInt(cursor.getColumnIndex(setDBlite.tblMascotas.COL_activo))
                idEspecie = cursor.getInt(cursor.getColumnIndex(setDBlite.tblMascotas.COL_idEspecie))
                idUsuario = cursor.getInt(cursor.getColumnIndex(setDBlite.tblMascotas.COL_idUsuario))

                val byteArrayList: MutableList<ByteArray> = mutableListOf()

                val queryImages =
                    "SELECT * FROM ${setDBlite.tblImagenesMascota.Table_Name} WHERE ${setDBlite.tblImagenesMascota.COL_idMascota} = $idMascota "
                val cursorImages = dataBase.rawQuery(queryImages, null)
                if (cursorImages.moveToFirst()) {
                    do {
                        idMascotaD = cursorImages.getInt(cursorImages.getColumnIndex(setDBlite.tblImagenesMascota.COL_idMascota))
                        img1 = cursorImages.getBlob(cursorImages.getColumnIndex(setDBlite.tblImagenesMascota.COL_imagen))
                        img2 = cursorImages.getBlob(cursorImages.getColumnIndex(setDBlite.tblImagenesMascota.COL_imagen2))
                        img3 = cursorImages.getBlob(cursorImages.getColumnIndex(setDBlite.tblImagenesMascota.COL_imagen3))

                        if (img1 != null) {
                            byteArrayList.add(img1)
                        }
                        if (img2 != null) {
                            byteArrayList.add(img2)
                        }
                        if (img3 != null) {
                            byteArrayList.add(img3)
                        }

                        mascotaDetails.append("Imagenes Post:\n")
                        mascotaDetails.append("IdPostMult: $idMascotaD\n")
                        mascotaDetails.append("Img : $img1\n")
                        mascotaDetails.append("Img 2: $img2\n")
                        mascotaDetails.append("Img 3: $img3\n")

                    } while (cursorImages.moveToNext())
                }
                Log.e("NumImages", "Número de imágenes para la mascota $idMascotaD: ${byteArrayList.size}")
                val mascotaOffline = MascotasOffline(
                    nombre,
                    edad,
                    raza,
                    activo,
                    idEspecie,
                    idUsuario,
                    idMascota,
                    byteArrayList.toList()
                )

                MascotasOfflineList.MascotasOfflineList += mascotaOffline

                mascotaDetails.append("Id: $idMascotaD\n")
                mascotaDetails.append("Name: $nombre\n")
                mascotaDetails.append("Description: $edad\n")
                mascotaDetails.append("IdUser: $raza\n")
                mascotaDetails.append("Pais: $activo\n")
                mascotaDetails.append("Active : $idEspecie\n")
                mascotaDetails.append("Active : $idUsuario\n")

            } while (cursor.moveToNext())
        }

        cursor.close()
        dataBase.close()

        Log.e("Posts", mascotaDetails.toString())
    }

    fun showUserDetails(context: Context): UserOffline? {
        val dataBase = this.readableDatabase
        val query = "SELECT * FROM ${setDBlite.tblUsuarios.Table_Name}"
        val cursor = dataBase.rawQuery(query, null)

        var idUsuario: Int?=null
        var sexo: Int?=null
        var nombre: String?=null
        var telefono: String?=null
        var passw: String?=null
        var correo: String?=null
        var imagen: ByteArray?=null
        var fechaNacimiento: String?=null
        var userOffline: UserOffline?=null

        val userDetails = StringBuilder()

        if (cursor.moveToFirst()) {
            do {
                idUsuario = cursor.getInt(cursor.getColumnIndex(setDBlite.tblUsuarios.IDUSUARIO))
                sexo = cursor.getInt(cursor.getColumnIndex(setDBlite.tblUsuarios.SEXO))
                nombre = cursor.getString(cursor.getColumnIndex(setDBlite.tblUsuarios.NOMBRE))
                telefono = cursor.getString(cursor.getColumnIndex(setDBlite.tblUsuarios.TELEFONO))
                passw = cursor.getString(cursor.getColumnIndex(setDBlite.tblUsuarios.PASSW))
                correo = cursor.getString(cursor.getColumnIndex(setDBlite.tblUsuarios.CORREO))
                imagen = cursor.getBlob(cursor.getColumnIndex(setDBlite.tblUsuarios.IMAGEN))
                fechaNacimiento = cursor.getString(cursor.getColumnIndex(setDBlite.tblUsuarios.FECHANACIMIENTO))

                userDetails.append("Inicio: ")
                userDetails.append("ID: $idUsuario\n")
                userDetails.append("nombre: $nombre\n")
                userDetails.append("sexo: $sexo\n")
                userDetails.append("telefono: $telefono\n")
                userDetails.append("Email: $correo\n")
                userDetails.append("Password: $passw\n")
                userDetails.append("Image URL: $imagen\n\n")
                userDetails.append("fechaNacimiento: $fechaNacimiento\n\n")

                userOffline = UserOffline(
                    idUsuario,
                    sexo,
                    nombre,
                    telefono,
                    passw,
                    correo,
                    imagen,
                    fechaNacimiento
                )


            } while (cursor.moveToNext())
        }


        cursor.close()
        dataBase.close()

        Toast.makeText(context, userDetails.toString(), Toast.LENGTH_LONG)
        Log.e("Terminado",userDetails.toString())
        return userOffline
    }

    fun deleteMascota(postId: Int) {
        val dataBase = writableDatabase
        val tableName = setDBlite.tblMascotas.Table_Name
        val whereClause = "${setDBlite.tblMascotas.COL_idMascota} = ?"
        val whereArgs = arrayOf(postId.toString())

        dataBase.delete(tableName, whereClause, whereArgs)
        dataBase.close()
    }

    fun deleteUser(iduser: Int?) {
        val dataBase = writableDatabase
        val tableName = setDBlite.tblUsuarios.Table_Name
        val whereClause = "${setDBlite.tblUsuarios.IDUSUARIO} = ?"
        val whereArgs = arrayOf(iduser.toString())

        dataBase.delete(tableName, whereClause, whereArgs)
        dataBase.close()
    }
}