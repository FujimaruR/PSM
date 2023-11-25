<?php


include_once 'DAO_Mascotas.php';
include_once 'DB_Conection.php';

    $Mascota = new Mascotas();

    $especie = $_POST['especie'];
    
    $res = $Mascota->InsertEspecie($especie);
    if ($res) {
    http_response_code(200); // Éxito en la inserción de especie
    echo json_encode(true);
} else {
    http_response_code(500); // Error en la inserción de especie
    echo json_encode(false);
}


?>