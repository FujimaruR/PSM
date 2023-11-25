<?php


include_once 'DAO_Mascotas.php';

    $Mascota = new Mascotas();

    $especie = $_POST['especie'];
    $idEspecie = $_POST['idEspecie'];
    
    $res = $Mascota->EditarEspecie($especie , $idEspecie);
    if($res){
        echo json_encode($res);
    }


?>