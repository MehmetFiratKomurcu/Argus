<?php

include_once 'db.php';
include_once 'db_class.php';
include_once 'advertisements_class.php';

$database = new Database();
$db = $database->getConnection();

$Advertisements = new ArgusAdvertisements($db);

$pCompanyName = $_POST["CompanyName"];
$pLocation = $_POST["Location"];
$pCampaign_Type = $_POST["Campaign_Type"];
$pdate = $_POST["date"];
$pContext = $_POST["Context"];

//echo("$pCompanyName + $pCampaign_Type + $pLocation + $pdate");
/*
$pCompanyName = $connection->real_escape_string($pCompanyName);
$pLocation = $connection->real_escape_string($pLocation);
$pCampaign_Type = $connection->real_escape_string($pCampaign_Type);
$pdate = $connection->real_escape_string($pdate);
$pContext = $connection->real_escape_string($pContext);
*/

$findme   = ',';
$pos = strpos($pLocation, $findme);
if($pos === false) {
    echo "The string '$findme' was not found in the string '$mystring'";
}else {
    $pieces = explode(",", $pLocation);
    if(is_numeric($pieces[0]) && is_numeric($pieces[1])) {
        $stmt = $Advertisements->insertCampaign($pCompanyName, $pLocation, $pContext, $pdate, $pCampaign_Type);
    } else {
        echo "Location is not valid.";
    }
}




?>
