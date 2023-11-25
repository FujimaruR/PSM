<?php

include_once 'DAO_Usuarios.php';
include_once 'DB_Conection.php';

class ObtenerSingleton extends DB
{
    public function ObtenerDatos()
    {
        // Inicio de sesion
        $email = $_POST['email'];

        $getData = new Usuarios();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $getData->ObtenerDatos($pdo, $email);

                if ($result) {
                    echo $result;
                }
                else{
                    echo "no se han encontrado los datos";
                }
            } catch (PDOException $e) {
                return "Error: " . $e->getMessage();
            }
        }
    }

}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $ObtencionDatos = new ObtenerSingleton();
    $result = $ObtencionDatos->ObtenerDatos();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>