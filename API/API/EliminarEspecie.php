<?php


include_once 'DAO_Mascotas.php';

    $Mascota = new Mascotas();

    $idEspecie = $_POST['idEspecie'];
    
    $res = $Mascota->EliminarEspecie($idEspecie);
    if($res){
        echo json_encode($res);
    }

?>