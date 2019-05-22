 <?php
  class ArgusUsers {
    private $conn;
    private $table = "Users";

    public $username;
    public $password;

    public function __construct($db){
      $this->conn = $db;
    }
    function read(){

      // select all query
      $query = "SELECT * FROM Users";
      $stmt = $this->conn->prepare($query);
      $stmt->execute();

      return $stmt;
    }

    function changePass($username, $new_pass){
      $query = "UPDATE " . $this->table . " SET password = '$new_pass' WHERE username =  '$username'";
      //echo($query);
      $isItDone = False;
      try{
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        $isItDone = True;
      }catch(Exception $e) {
        $isItDone = False;
      }
      return $isItDone;
    }

    function register($rusername, $rpassword) {
      $query = "INSERT INTO $this->table (username, password) VALUES('$rusername', '$rpassword')";
      echo($query);
      $isItDone = False;
      try{
        $stmt = $this->conn->prepare($query);
        $stmt->execute();
        $isItDone = True;
      }catch(Exception $e) {
        $isItDone = False;
      }
      return $isItDone;
    }
  }
 ?>
