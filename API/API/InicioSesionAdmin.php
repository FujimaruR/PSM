<?php


include_once 'DAO_Usuarios.php';
include_once 'DB_Conection.php';

    $Usuario = new Usuarios();

    $email = $_POST['email'];
    $password = $_POST['password'];
    
    $res = $Usuario->getAdmin($email,$password);
    if($res){
        echo json_encode($res);
    }


?>