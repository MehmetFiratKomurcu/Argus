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
$stmt = $Advertisements->insertCampaign($pCompanyName, $pLocation, $pContext, $pdate, $pCampaign_Type);

?>
