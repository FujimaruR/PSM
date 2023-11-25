<?php


include 'DB_Conection.php';



class Usuarios extends DB
{
    public function InicioSesion($pdo, $email, $password)
    {
        try {
            $sql = "SELECT idUsuario FROM tb_Usuario WHERE correo = :email AND passw = :p_password";
            $stmt = $pdo->prepare($sql);

            $stmt->bindParam(':email', $email, PDO::PARAM_STR);
            $stmt->bindParam(':password', $password, PDO::PARAM_STR);
            $stmt->execute();

            $result = $stmt->fetch(PDO::FETCH_ASSOC);

            $pdo = null;

            if ($result) {
                return "El inicio de sesión ha sido exitoso"; // El usuario y la contraseña existen en la tabla
            } else {
                return "Las credenciales que has introducido no son correctas"; // El usuario y/o la contraseña no coinciden
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    function getUsuarioUnique($email,$password){

        try{
            $con = $this->connect();

            $query = "SELECT * FROM `tb_Usuario` WHERE correo = :email AND passw = :passw";
            $exec = $con->prepare($query);

            $exec->bindParam(':email', $email);
            $exec->bindParam(':passw', $password);
    
            $exec->execute();
    
            if($exec->rowCount() > 0){
                $user = $exec->fetch(PDO::FETCH_ASSOC);
                $exec = null;
                return $user;
            }else{
                $exec = null;
                return false;
            }
        }catch(PDOException $e){
            echo "Error: " . $e->getMessage();
            $exec = null;
            return false;
        }

    }


    function getAdmin($email,$password){

        try{
            $con = $this->connect();

            $query = "SELECT correo, passw FROM `tb_Admin` WHERE correo = :email AND passw = :passw";
            $exec = $con->prepare($query);

            $exec->bindParam(':email', $email);
            $exec->bindParam(':passw', $password);
    
            $exec->execute();
    
            if($exec->rowCount() > 0){
                $user = $exec->fetch(PDO::FETCH_ASSOC);
                $exec = null;
                return $user;
            }else{
                $exec = null;
                return false;
            }
        }catch(PDOException $e){
            echo "Error: " . $e->getMessage();
            $exec = null;
            return false;
        }

    }


    public function Registro($pdo, $idSexo, $email, $password, $nombre, $telefono, $fechaNacimiento, $direccion, $imgContenido)
    {
        try {
            $sql = "INSERT INTO tb_Usuario (idSexo ,correo, passw, nombre, telefono, fechaNacimiento, direccion, imagen) 
                    VALUES (:p_sexo, :email, :p_password, :nombre, :telefono, :fechaNacimiento, :direccion, :imagen)";
            $stmt = $pdo->prepare($sql);

            $stmt->bindParam(':p_sexo', $idSexo, PDO::PARAM_INT);
            $stmt->bindParam(':email', $email, PDO::PARAM_STR);
            $stmt->bindParam(':p_password', $password, PDO::PARAM_STR);
            $stmt->bindParam(':nombre', $nombre, PDO::PARAM_STR);
            $stmt->bindParam(':telefono', $telefono, PDO::PARAM_STR);
            $stmt->bindParam(':fechaNacimiento', $fechaNacimiento, PDO::PARAM_STR);
            $stmt->bindParam(':direccion', $direccion, PDO::PARAM_STR);
            $stmt->bindParam(':imagen', $imgContenido, PDO::PARAM_LOB);
            
            $result = $stmt->execute();
    
            $pdo = null;
    
            if ($result) {
                return true; // El registro se insertó correctamente
            } else {
                return false; // No se pudo insertar el registro
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    public function ModificarFoto($pdo, $idUsuario, $imgContenido)
    {
        try {
            $sql = "UPDATE tb_Usuario SET imagen = :imagen WHERE idUsuario = :idUsuario";
            $stmt = $pdo->prepare($sql);
    
            $stmt->bindParam(':imagen', $imgContenido, PDO::PARAM_LOB);
            $stmt->bindParam(':idUsuario', $idUsuario, PDO::PARAM_INT);
    
            $result = $stmt->execute();
    
            $pdo = null;
    
            if ($result) {
                return true; // La foto se modificó correctamente
            } else {
                return false; // No se pudo modificar la foto
            }
        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }
    

    public function ModificarUsuario($pdo, $idUsuario, $idSexo, $email, $password, $nombre, $telefono, $fechaNacimiento, $direccion, $imagen)
    {
        try {
            $sql = "UPDATE tb_Usuario SET idSexo = :idSexo, correo = :email, passw = :p_password, nombre = :nombre, telefono = :telefono, fechaNacimiento = :fechaNacimiento, direccion = :direccion, imagen = :imagen WHERE idUsuario = :idUsuario";
            $stmt = $pdo->prepare($sql);
    
            $stmt->bindParam(':idSexo', $idSexo, PDO::PARAM_INT);
            $stmt->bindParam(':email', $email, PDO::PARAM_STR);
            $stmt->bindParam(':p_password', $password, PDO::PARAM_STR);
            $stmt->bindParam(':nombre', $nombre, PDO::PARAM_STR);
            $stmt->bindParam(':telefono', $telefono, PDO::PARAM_STR);
            $stmt->bindParam(':fechaNacimiento', $fechaNacimiento, PDO::PARAM_STR);
            $stmt->bindParam(':direccion', $direccion, PDO::PARAM_STR);
            $stmt->bindParam(':idUsuario', $idUsuario, PDO::PARAM_INT);
            $stmt->bindParam(':imagen', $imagen, PDO::PARAM_LOB);

            $pdo = null;

    
            if ($stmt->execute()) {
                return true; // Los datos del usuario se modificaron correctamente
            } else {
                return false; // No se pudieron modificar los datos del usuario
            }
    

        } catch (PDOException $e) {
            return "Error: " . $e->getMessage();
        }
    }

    public function ObtenerDatos($pdo, $email)
    {
        try {
            $sql = "SELECT idUsuario, idSexo, nombre, telefono, passw, correo, imagen, fechaNacimiento, direccion, fechaRegistro 
                    FROM tb_Usuario WHERE correo = :email";
            $stmt = $pdo->prepare($sql);
            $stmt->bindParam(':email', $email, PDO::PARAM_STR);
            $stmt->execute();
        
            $usuario = $stmt->fetch(PDO::FETCH_ASSOC);
        
            if ($usuario) {
                return $usuario;
            } else {
                echo "Error";
            }
        } catch (PDOException $e) {
            echo "Error: " . $e->getMessage();
        }
    }


}

?>