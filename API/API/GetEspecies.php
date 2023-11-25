<?php


include_once 'DAO_Mascotas.php';

    $Mascota = new Mascotas();
    
    $res = $Mascota->ObtenEspecies();
    if ($res) {
        // Realizar acciones con los datos obtenidos
        echo json_encode($res);
    } else {
        // No se encontraron registros en la tabla
        echo json_encode([]);
    }


?>