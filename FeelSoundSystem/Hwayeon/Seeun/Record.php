<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];

  $statement = mysqli_query($conn, "SELECT * FROM HW_RECORD WHERE U_ID = '".$U_ID."'");
  //$response = new StdClass();
  $response = array(
    array()
  );
  //$response->result = "FAILED";
  $i=0;
  if($statement->num_rows){
    //$response["COUNT"]=$statement->num_rows;
    while($row=mysqli_fetch_assoc($statement)) { //prepared statement로부터 반환된 레코드셋의 바인딩 변수에 저장한다
          $response[$i]["success"] = true;
          $response[$i]["S_NUM"] = $row["S_NUM"];
          $response[$i]["S_NAME"] = $row["S_NAME"];
          $response[$i]["TIME"] = $row["TIME"];
          $i=$i+1;
      }
  }
  else{
    $respones[0]["success"] = false;
    $response[0]["S_NUM"] = 0;
    $respones[1]["success"] = false;
    $response[1]["S_NUM"] = 0;
  }
  header('Content-Type: application/json');
  echo json_encode($response, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE);

  mysqli_close($conn);

?>
