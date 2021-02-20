<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];
  $PW = $_GET['pass'];

  $statement = mysqli_prepare($conn, "SELECT * FROM HW_USER WHERE U_ID = ? AND PW = ?");
  mysqli_stmt_bind_param($statement, "ss", $U_ID, $PW);
  mysqli_stmt_execute($statement);
  mysqli_stmt_store_result($statement); //prepared statement로부터 생성된 레코드셋을 가져아 클라이언트에 저장한다
  mysqli_stmt_bind_result($statement, $U_ID, $PW, $NAME, $CONTACT); //검색 결과로 반환되는 레코드셋의 필드를 php변수에 바인딩한다

  //$response = new StdClass();
  $response = array();
  //$response->result = "FAILED";
  $response["success"] = false;

  while(mysqli_stmt_fetch($statement)) { //prepared statement로부터 반환된 레코드셋의 바인딩 변수에 저장한다
        //$response->result = "SUCCESS";
        $response["success"] = true;
        $response["U_ID"] = $U_ID;
        $response["PW"] = $PW;
        $response["NAME"] = $NAME;
        $response["CONTACT"] = $CONTACT;
/*
        $user["U_ID"] = $U_ID;
        $user["PW"] = $PW;
        $user["NAME"] = $NAME;
        $user["CONTACT"] = $CONTACT;*/
  }

  header('Content-Type: application/json');
  //echo json_encode($user);
  echo json_encode($response, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE);

  mysqli_close($conn);

?>
