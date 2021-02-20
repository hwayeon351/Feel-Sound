<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];
  $CONTENT = $_GET['CONTENT'];
  $TIME = $_GET['TIME'];

  $response = new StdClass();

  //echo($U_ID+$TIME);

/*
  $statement = mysqli_prepare($conn, "SELECT * FROM HW_SUGGESTION WHERE U_ID = ? AND CONTENT = ?");
  mysqli_stmt_bind_param($statement, "ss", $U_ID, $CONTENT);
  $exCnt = mysqli_num_rows($statement);

  $statement1 = mysqli_prepare($conn, "SELECT * FROM HW_SUGGESTION_S WHERE U_ID = ? AND S_NAME = ?");
  mysqli_stmt_bind_param($statement1, "ss", $U_ID, $S_NAME);
  $exCnt1 = mysqli_num_rows($statement1);

  $statement2 = mysqli_prepare($conn, "SELECT * FROM HW_SUGGESTION_S WHERE S_NAME = ?");
  mysqli_stmt_bind_param($statement2, "s", $S_NAME);
  $exCnt2 = mysqli_num_rows($statement2);
*/

  $statement = mysqli_query($conn, "SELECT * FROM HW_SUGGESTION WHERE U_ID = '".$U_ID."' AND CONTENT = '".$CONTENT."'");

  if($statement->num_rows){
      $response->result = "overlap";
  }
  else{ // 기존 db에 존재하지 않음
    if($CONTENT!=''){
      if(mysqli_query($conn,"INSERT INTO HW_SUGGESTION(U_ID, CONTENT, TIME) VALUES('".$U_ID."','".$CONTENT."','".$TIME."');")){
        $response->result = "SUCCESS";
      }else{
        $response->result = "FAILED";
      }
    }
    else{
      $response->result = "blank";
    }
  }



  header('Content-Type: application/json');
  echo json_encode($response, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE);

  mysqli_close($conn);

?>
