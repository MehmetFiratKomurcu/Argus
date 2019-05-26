<?php
  class ArgusAdvertisements {
    private $conn;
    private $table = "Advertisements";

    public $CompanyID;
    public $CompanyName;
    public $CompanyLocation;
    public $CampaignContext;
    public $CampaignDeadline;

    public function __construct($db){
      $this->conn = $db;
    }

    function readWithBoth($pCompanyName, $pCategory) {
      $pCompanyName = strtolower($pCompanyName);
      $query = "SELECT * FROM Advertisements WHERE CompanyName LIKE '{%$pCompanyName%}' and Category = '$pCategory'";
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }

    function readWithCompanyName($pCompanyName) {
      $pCompanyName = strtolower($pCompanyName);
      $query = "SELECT * FROM Advertisements WHERE CompanyName LIKE '%{$pCompanyName}%'";
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }

    function readWithCategory($pCategory) {
      $query = "SELECT * FROM Advertisements WHERE Category = '$pCategory'";
      $stmt = $this->conn->prepare($query);
      //echo($query."df");
      $stmt->execute();
      //echo($query);
      return $stmt;
    }

    function read(){

      // select all query
      $query = "SELECT * FROM Advertisements";
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }

    function insertCampaign($pCompanyName, $pLocation, $pContext, $date, $pCampaign_Type) {
      $pCompanyName = strtolower($pCompanyName);
      $query = "INSERT INTO Advertisements (CompanyName, CompanyLocation, CampaignContext, CampaignDeadline, Category)
      VALUES ('$pCompanyName', '$pLocation', '$pContext', '$date', '$pCampaign_Type')";
      echo($query);
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }
  }
 ?>
 
