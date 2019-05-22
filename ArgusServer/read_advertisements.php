<?php
  header("Access-Control-Allow-Origin: *");
  header("Content-Type: application/json; charset=UTF-8");
  include_once 'db_class.php';
  include_once 'advertisements_class.php';

  $database = new Database();
  $db = $database->getConnection();

  $Advertisements = new ArgusAdvertisements($db);

  $stmt = $Advertisements->read();
  $num = $stmt->rowCount();
  //echo($num);
  if($num>0){

    // adv. array
    $advertisements_arr=array();
    $advertisements_arr["records"]=array();
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)){
        extract($row);
        $advertisements_item=array(
            "CompanyID" => $CompanyID,
            "CompanyName" => $CompanyName,
            "CompanyLocation" => $CompanyLocation,
            "CampaignContext" => $CampaignContext,
            "CampaignDeadline" => $CampaignDeadline
        );

        array_push($advertisements_arr["records"], $advertisements_item);        
    }

    // set response code - 200 OK
    http_response_code(200);

    // show products data in json format
    echo json_encode($advertisements_arr);
}else{

    // set response code - 404 Not found
    http_response_code(404);

    // tell the user no products found
    echo json_encode(
        array("message" => "No Advertisements found.")
    );
}
 ?>
 
