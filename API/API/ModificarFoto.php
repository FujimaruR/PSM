<?php


include_once 'DAO_Usuarios.php';
include_once 'DB_Conection.php';

class ModificarFoto extends DB
{
    public function EditarFoto()
    {
        // Inicio de sesion

        $idUsuario = $_POST['idUsuario'];

        $base64ImageString = $_POST['imagen'];

        // Elimina el prefijo "data:image/png;base64," si está presente
        $base64ImageString = str_replace('data:image/png;base64,', '', $base64ImageString);
        $base64ImageString = str_replace(' ', '+', $base64ImageString);

        // Decodifica la cadena base64 a bytes
        $imgContenido = base64_decode($base64ImageString);
        $modificarFoto = new Usuarios();

        $db = new DB();
        $pdo = $db->connect();
        if ($pdo) {
            try {
                $result = $modificarFoto->ModificarFoto($pdo, $idUsuario, $imgContenido);

                if (!$result) {
                    echo "No se ha podido modificar la imagen";
                }
                else{
                    echo "Se ha modificado la imagen";
                }
            } catch (PDOException $e) {
                return "Error: " . $e->getMessage();
            }
        }
    }

}

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $nuevaFoto = new ModificarFoto();
    $result = $nuevaFoto->EditarFoto();
    if ($result === true) {
    } else {
        echo $result;
    }
}

?>