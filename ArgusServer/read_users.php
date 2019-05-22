<?php
  header("Access-Control-Allow-Origin: *");
  header("Content-Type: application/json; charset=UTF-8");
  include_once 'db_class.php';
  include_once 'users_class.php';

  $database = new Database();
  $db = $database->getConnection();

  $users = new ArgusUsers($db);

  $stmt = $users->read();
  $num = $stmt->rowCount();
  $uusername = $_GET["username"];
  $upassword = $_GET["password"];
  //echo($num);
  if($num>0){

    // users array
    $users_arr=array();
    $users_arr["records"]=array();
    $isThere = False;
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        // extract row
        // this will make $row['name'] to
        // just $name only
        extract($row);
        //echo($uusername." ".$upassword);
        //echo($username." ".$password);
        if($username == $uusername && $password == $upassword) {
            $isThere = True;
        }
    }

    if($isThere) {
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
}else{

    // set response code - 404 Not found
    http_response_code(404);

    // tell the user no products found
    echo json_encode(
        array("message" => "No users found.")
    );
}
 ?>
 
