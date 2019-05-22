<?php

    header("Access-Control-Allow-Origin: *");
    header("Content-Type: application/json; charset=UTF-8");
    include_once 'db_class.php';
    include_once 'users_class.php'; 

    $database = new Database();
    $db = $database->getConnection();
    $users = new ArgusUsers($db);
    $uusername = $_GET["username"];
    $upassword = $_GET["password"];
    $users_arr=array();
    $users_arr["records"]=array();
    $resp = $users->changePass($uusername, $upassword);
    
    if($resp) {
        $users_item=array(
            "Status" => "True" 
        );
        array_push($users_arr["records"], $users_item);
    }else {
        $users_item=array(
            "Status" => "False" 
        );
        array_push($users_arr["records"], $users_item);
    }
        // set response code - 200 OK
        http_response_code(200);

        // show products data in json format
        echo json_encode($users_arr);

?> 
