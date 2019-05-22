<?php
  class Database{
    private $host = "argusdb.ch33r4cswyrr.us-east-1.rds.amazonaws.com:3306";
    private $db_name = "innodb";
    private $username = "admin";
    private $password = "12345678";
    private $conn;

    public function getConnection(){
      $this->conn = null;
      try{
            $this->conn = new PDO("mysql:host=" . $this->host . ";dbname=" . $this->db_name, $this->username, $this->password);
            $this->conn->exec("set names utf8");
            //echo("Connection Established");
        }catch(PDOException $exception){
            echo "Connection error: " . $exception->getMessage();
        }

      return $this->conn;
    }
  }
 ?>
