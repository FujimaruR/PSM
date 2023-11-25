<?php

include_once 'DAO_Usuarios.php';
include_once 'DB_Conection.php';

class Registro extends DB
{
    public function Registrarse()
    {
        // Inicio de sesion
        $email = $_POST['email'];
        $password = $_POST['password'];
        $nombre = $_POST['nombre'];
        $telefono = $_POST['telefono'];
        $password = $_POST['password'];
        $fechaNacimiento = $_POST['fechaNacimiento'];
        $direccion = $_POST['direccion'];
        $idSexo = $_POST['idSexo'];

        // Obtiene la cadena de la imagen desde el cuerpo del POST
        $base64ImageString = $_POST['imagen'];

        // Elimina el prefijo "data:image/png;base64," si está presente
        $base64ImageString = str_replace('data:image/png;base64,', '', $base64ImageString);
        $base64ImageString = str_replace(' ', '+', $base64ImageString);

        // Decodifica la cadena base64 a bytes
        $imgContenido = base64_decode($base64ImageString);

        $registro = new Usuarios();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $registro->Registro($pdo, $idSexo, $email, $password, $nombre, $telefono, $fechaNacimiento, $direccion, $imgContenido);

                if ($result) {
                    echo $result;
                }
                else {
                    echo $result;
                }
            } catch (PDOException $e) {
                return "Error: " . $e->getMessage();
            }
        }
    }

}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $Registrarse = new Registro();
    $result = $Registrarse->Registrarse();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>