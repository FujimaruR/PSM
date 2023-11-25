<?php

include_once 'DAO_Mascotas.php';
include_once 'DB_Conection.php';

class ModificarMascota extends DB
{
    public function ModificarMascota()
    {
        // Inicio de sesion
        $nombre = $_POST['nombre'];
        $edad = $_POST['edad'];
        $raza = $_POST['raza'];
        $activo = $_POST['activo'];
        $idEspecie = $_POST['idEspecie'];
        $idMascota = $_POST['idMascota'];

        // Obtiene la cadena de la imagen desde el cuerpo del POST

        $alta = new Mascotas();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $alta->ModificarMascota($pdo, $nombre, $edad, $raza, $activo, $idEspecie, $idMascota);

                if ($result) {
                    echo "Se ha podido Modificar exitosamente";
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
    $ModificacionMascota = new ModificarMascota();
    $result = $ModificacionMascota->ModificarMascota();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>