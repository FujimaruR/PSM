<?php


include 'DB_Conection.php';
class Mascotas extends DB
{

   public function Registro($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idUsuario, $imagen, $imagen2, $imagen3)
{
    try {
        $sql = "INSERT INTO tb_Mascotas (nombre, edad, raza, activo, idEspecie, idUsuario) 
                VALUES (:p_nombre, :p_edad, :p_raza, :p_activo, :p_idEspecie, :p_idUsuario)";
        $stmt = $pdo->prepare($sql);

        $stmt->bindParam(':p_nombre', $nombre, PDO::PARAM_STR);
        $stmt->bindParam(':p_edad', $edad, PDO::PARAM_INT);
        $stmt->bindParam(':p_raza', $raza, PDO::PARAM_STR);
        $stmt->bindParam(':p_activo', $activo, PDO::PARAM_INT);
        $stmt->bindParam(':p_idEspecie', $idEspecie, PDO::PARAM_INT);
        $stmt->bindParam(':p_idUsuario', $idUsuario, PDO::PARAM_INT);

        $result = $stmt->execute();

        $lastInsertId = $pdo->lastInsertId(); // Obtiene el ID del último registro insertado

        if ($result) {
            $sql = "INSERT INTO tb_ImagenesMascota (idMascota, imagen, imagen2, imagen3) 
                    VALUES (:p_idMascota, :p_imagen, :p_imagen2, :p_imagen3)";
            $stmt = $pdo->prepare($sql);

            $stmt->bindParam(':p_idMascota', $lastInsertId, PDO::PARAM_INT);
            $stmt->bindParam(':p_imagen', $imagen, PDO::PARAM_LOB);
            $stmt->bindParam(':p_imagen2', $imagen2, PDO::PARAM_LOB);
            $stmt->bindParam(':p_imagen3', $imagen3, PDO::PARAM_LOB);

            $result = $stmt->execute();

            if ($result) {
                return $lastInsertId; // Devuelve el ID del último registro insertado
            } else {
                return false; // No se pudo insertar el registro en la tabla de imágenes
            }
        } else {
            return false; // No se pudo insertar el registro en la tabla de mascotas
        }
    } catch (PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}


    public function ModificarMascota($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idMascota)
    {
        try {
            $sql = "UPDATE tb_Mascotas SET nombre = :nombre, edad = :edad, raza = :raza, activo = :activo, idEspecie = :idEspecie WHERE idMascota = :idMascota";
            $stmt = $pdo->prepare($sql);
    
            $stmt->bindParam(':nombre', $nombre, PDO::PARAM_STR);
            $stmt->bindParam(':edad', $edad, PDO::PARAM_INT);
            $stmt->bindParam(':raza', $raza, PDO::PARAM_STR);
            $stmt->bindParam(':activo', $activo, PDO::PARAM_INT);
            $stmt->bindParam(':idEspecie', $idEspecie, PDO::PARAM_INT);
            $stmt->bindParam(':idMascota', $idMascota, PDO::PARAM_INT);
    
            $result = $stmt->execute();
    
            $pdo = null;
    
            if ($result) {
                return true; // Los datos del usuario se modificaron correctamente
            } else {
                return false; // No se pudieron modificar los datos del usuario
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    function insertOfflinePosts($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idUsuario)
    {
        try {
            $sql = "INSERT INTO tb_Mascotas (nombre, edad, raza, activo, idEspecie, idUsuario) 
                    VALUES (:p_nombre, :p_edad, :p_raza, :p_activo, :p_idEspecie, :p_idUsuario)";
            $stmt = $pdo->prepare($sql);
    
            $stmt->bindParam(':p_nombre', $nombre, PDO::PARAM_STR);
            $stmt->bindParam(':p_edad', $edad, PDO::PARAM_INT);
            $stmt->bindParam(':p_raza', $raza, PDO::PARAM_STR);
            $stmt->bindParam(':p_activo', $activo, PDO::PARAM_INT);
            $stmt->bindParam(':p_idEspecie', $idEspecie, PDO::PARAM_INT);
            $stmt->bindParam(':p_idUsuario', $idUsuario, PDO::PARAM_INT);
    
            $result = $stmt->execute();
    
            $lastInsertId = $pdo->lastInsertId(); // Obtiene el ID del último registro insertado
    
            if ($result) {
                return $lastInsertId; // Devuelve el ID del último registro insertado
            } else {
                return false; // No se pudo insertar el registro
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }


    function InsertEspecie($especie)
    {
        try {

            $con = $this->connect();

            $sql = "INSERT INTO tb_Especies (nombre, estatus) 
                    VALUES (:p_nombre, :p_estatus)";

                    
            $stmt = $con->prepare($sql);

            $estatusSubida = 1;
    
            $stmt->bindParam(':p_nombre', $especie, PDO::PARAM_STR);
            $stmt->bindParam(':p_estatus', $estatusSubida, PDO::PARAM_INT);
    
    
            $result = $stmt->execute();
    
            $pdo = null;
    
            if ($result) {
                return true; // Los datos del usuario se modificaron correctamente
            } else {
                return false; // No se pudieron modificar los datos del usuario
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    function ObtenEspecies()
    {
        try {

            $con = $this->connect();

            $sql =  "SELECT * FROM tb_Especies WHERE activo = 1";
                    
            $stmt = $con->prepare($sql);
    
            $stmt->execute();
            $result = $stmt->fetchAll(PDO::FETCH_ASSOC);
            $con = null;
    
            return $result;
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    function EditarEspecie($nombre, $idEspecie)
{
    try {
        $con = $this->connect();
        
        // UPDATE query
        $sql = "UPDATE tb_Especies SET nombre = :nombre WHERE idEspecie = :idEspecie";

        // Prepare statement
        $stmt = $con->prepare($sql);

        // Bind parameters
        $stmt->bindParam(':nombre', $nombre);
        $stmt->bindParam(':idEspecie', $idEspecie);

        // Execute the UPDATE statement
        $stmt->execute();

        // Check if any row is affected
        if ($stmt->rowCount() > 0) {
            $res = "Especie actualizada correctamente.";
        } else {
            $res = "No se ha podido actualizar la especie. Por favor, comprueba si el id de especie es correcto y vuelve a intentarlo.";
        }

        $con = null;
        return $res;

    } catch (PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}


function EliminarEspecie($idEspecie)
{
    try {
        $con = $this->connect();
        
        // UPDATE query
        $sql = "UPDATE tb_Especies SET activo = :activo WHERE idEspecie = :idEspecie";

        // Prepare statement
        $stmt = $con->prepare($sql);

        $activo = false;
        // Bind parameters
        $stmt->bindParam(':activo', $activo);
        $stmt->bindParam(':idEspecie', $idEspecie);

        // Execute the UPDATE statement
        $stmt->execute();

        // Check if any row is affected
        if ($stmt->rowCount() > 0) {
            $res = "Especie eliminada correctamente.";
        } else {
            $res = "No se ha podido eliminar la especie. Por favor, comprueba si el id de especie es correcto y vuelve a intentarlo.";
        }

        $con = null;
        return $res;

    } catch (PDOException $e) {
        return "Error: " . $e->getMessage();
    }
}



}

?>