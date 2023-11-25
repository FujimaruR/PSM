<?php

include_once 'DAO_Mascotas.php';
include_once 'DB_Conection.php';

class RegistroMascotas extends DB
{
    public function RegistroMascotas()
    {
        // Inicio de sesion
        $nombre = $_POST['nombre'];
        $edad = $_POST['edad'];
        $raza = $_POST['raza'];
        $activo = $_POST['activo'];
        $idEspecie = $_POST['idEspecie'];
        $idUsuario = $_POST['idUsuario'];


        $base64ImageString = $_POST['imagen1'];

        // Elimina el prefijo "data:image/png;base64," si está presente
        $base64ImageString = str_replace('data:image/png;base64,', '', $base64ImageString);
        $base64ImageString = str_replace(' ', '+', $base64ImageString);

        // Decodifica la cadena base64 a bytes
        $imgContenido1 = base64_decode($base64ImageString);

        $base64ImageString = $_POST['imagen2'];

        // Elimina el prefijo "data:image/png;base64," si está presente
        $base64ImageString = str_replace('data:image/png;base64,', '', $base64ImageString);
        $base64ImageString = str_replace(' ', '+', $base64ImageString);

        // Decodifica la cadena base64 a bytes
        $imgContenido2 = base64_decode($base64ImageString);

        $base64ImageString = $_POST['imagen3'];

        // Elimina el prefijo "data:image/png;base64," si está presente
        $base64ImageString = str_replace('data:image/png;base64,', '', $base64ImageString);
        $base64ImageString = str_replace(' ', '+', $base64ImageString);

        // Decodifica la cadena base64 a bytes
        $imgContenido3 = base64_decode($base64ImageString);

        // Obtiene la cadena de la imagen desde el cuerpo del POST

        $alta = new Mascotas();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $alta->Registro($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idUsuario, $imgContenido1, $imgContenido2, $imgContenido3);

                if ($result) {
                    echo "Se ha registrado exitosamente";
                }
                else {
                    echo "No se ha podido registrar, contacta la administracion";
                }
            } catch (PDOException $e) {
                return "Error: " . $e->getMessage();
            }
        }
    }

}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $AltaMascotas = new RegistroMascotas();
    $result = $AltaMascotas->RegistroMascotas();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>