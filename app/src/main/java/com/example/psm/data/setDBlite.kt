package com.example.psm.data

class setDBlite {
    companion object{
        val DB_Name = "veterinaria"
        val DB_Version = 1
    }

    abstract class tblUsuarios{
        companion object{
            val Table_Name = "tb_Usuario"
            val IDUSUARIO = "idUsuario"
            val SEXO = "sexo"
            val NOMBRE = "nombre"
            val TELEFONO = "telefono"
            val PASSW = "passw"
            val CORREO = "correo"
            val IMAGEN = "imagen"
            val FECHANACIMIENTO = "fechaNacimiento"
            val DIRECCION = "direccion"
        }
    }

    abstract class tblMascotas{
        companion object{
            val Table_Name = "tb_Mascotas"
            val COL_idMascota = "idMascota"
            val COL_nombre = "nombre"
            val COL_edad = "edad"
            val COL_raza = "raza"
            val COL_activo = "activo"
            val COL_idEspecie = "idEspecie"
            val COL_idUsuario = "idUsuario"
        }
    }

    abstract class tblImagenesMascota{
        companion object{
            val Table_Name = "tb_Imagenes_Mascota"
            val COL_idImagenesMascota = "idImagenesMascota"
            val COL_idMascota = "idMascota"
            val COL_activo = "activo"
            val COL_imagen = "imagen"
            val COL_imagen2 = "imagenD"
            val COL_imagen3 = "imagenT"
        }
    }

    abstract class tblCitas{
        companion object{
            val Table_Name = "tb_Citas"
            val COL_idCita = "idCita"
            val COL_Hora = "Hora"
            val COL_idMascota = "idMascota"
            val COL_idUsuario = "idUsuario"
            val COL_idDoctor = "idDoctor"
            val COL_tipo = "tipo"
            val COL_activo = "activo"
            val COL_activoRecurrencia = "activoRecurrencia"
        }
    }
    abstract class tblDoctores{
        companion object{
            val Table_Name = "tb_Doctores"
            val COL_idDoctor = "idDoctor"
            val COL_nombre = "nombre"
            val COL_activo = "activo"
            val COL_horarioEntrada = "horarioEntrada"
            val COL_horarioSalida = "horarioSalida"
        }
    }

    abstract class tblEspecies{
        companion object{
            val Table_Name = "tb_Especies"
            val COL_idEspecie = "idEspecie"
            val COL_nombre = "nombre"
            val COL_activo = "activo"
        }
    }

}