<?php
  $server = "argusdb.ch33r4cswyrr.us-east-1.rds.amazonaws.com";
  $username = "admin";
  $password = "12345678";
  $db = "innodb";

  $connection = new mysqli($server, $username, $password, $db);
  $connection->query("SET NAMES utf8");

  if($connection -> connect_error){
    die("Connection failed: " . $connection -> connect_error);
  }else{
    //echo("Connection successful");
  }
?>
