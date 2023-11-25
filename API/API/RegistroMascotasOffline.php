<?php

include_once 'DAO_Mascotas.php';
include_once 'DB_Conection.php';

class RegistroMascotasOffline extends DB
{
    public function RegistroMascotasOff()
    {
        // Inicio de sesion
        $nombre = $_POST['nombre'];
        $edad = $_POST['edad'];
        $raza = $_POST['raza'];
        $activo = $_POST['activo'];
        $idEspecie = $_POST['idEspecie'];
        $idUsuario = $_POST['idUsuario'];

        // Obtiene la cadena de la imagen desde el cuerpo del POST

        $alta = new Mascotas();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $alta->insertOfflinePosts($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idUsuario);

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
    $AltaMascotasOffline = new RegistroMascotasOffline();
    $result = $AltaMascotasOffline->RegistroMascotasOff();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>