<?php


include_once 'DAO_Usuarios.php';


    $Usuario = new Usuarios();
    
     $email = $_POST['email'];
    $password = $_POST['password'];
    
    

   $res = $Usuario->getUsuarioUnique($email, $password);
    if($res){
    // Convertir el BLOB de la imagen en base64
    $base64Image = base64_encode($res['imagen']);
    
    // Reemplazar el BLOB de la imagen en el array con la versión base64
    $res['imagen'] = $base64Image;

    // Codificar el array como JSON y enviarlo
    $json = json_encode($res);
    if ($json === false) {
        // Codificación fallida, mostrar un mensaje de error
        echo "Error de codificación JSON: " . json_last_error_msg();
    } else {
        echo $json;
    }
}

?>

