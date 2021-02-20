<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];
  $PW = $_GET['pass'];
  $NAME = $_GET['name'];
  $CONTACT = $_GET['phone'];

  $checkExist = mysqli_query($conn, "SELECT * FROM HW_USER WHERE U_ID='".$U_ID."'");
  $exCnt = mysqli_num_rows($checkExist);

  $response = new StdClass();

  if($exCnt>0){
    $response->result = null;
  }
  else{
    if($U_ID!=''||$PW!=''||$NAME!=''||$CONTACT!=''){
    //mysqli_query($conn,"INSERT INTO Test(_id,pw) VALUES('".$_id."','".$pw."');"
      if(mysqli_query($conn,"INSERT INTO HW_USER(U_ID, PW, NAME, CONTACT) VALUES('".$U_ID."','".$PW."','".$NAME."','".$CONTACT."');")){
        $response->result = "SUCCESS";
        // $getPw = mysqli_fetch_array( mysqli_query($conn, "SELECT pw FROM TABLE WHERE id = $_id" ) );
        // $response->pw=$getPw[0];
      }
      else{
        $response->result = "FAILED";
      }
    }
    else{
      $response->result = "blank";
    }

  }
  //echo "INSERT INTO Test(_id,pw) VALUES('".$_id."','".$pw."');";

  header('Content-Type: application/json');
  echo json_encode($response, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE);

  mysqli_close($conn);

?>
