<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];
  $S_NAME = $_GET['S_NAME'];
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

  $statement1 = mysqli_query($conn, "SELECT * FROM HW_SUGGESTION_S WHERE U_ID = '".$U_ID."' AND S_NAME = '".$S_NAME."'");
  $statement2 = mysqli_query($conn, "SELECT * FROM HW_SUGGESTION_S WHERE S_NAME = '".$S_NAME."'");


  if($statement1->num_rows){
      $response->result = "overlap";
  }
  else{ // 기존 db에 존재하지 않음
    if($S_NAME!=''){
       //같은 이름을 가진 소리의 라벨이 있는지 검색
       //$exist_label_query = mysql_query($conn,"SELECT DISTINCT LABEL FROM HW_SUGGESTION_S WHERE S_NAME = '".$S_NAME."'");
       //$exist_sql = mysql_fetch_array($exist_label_query);
       $exist_sql = mysqli_query($conn,"SELECT DISTINCT LABEL FROM HW_SUGGESTION_S WHERE S_NAME = '".$S_NAME."'");
       if(!($exist_sql->num_rows)){ //라벨 없을때
           //$max_label_query = mysql_query($conn,"SELECT MAX(LABEL) AS max FROM HW_SUGGESTION_S");
           //$max_sql = mysql_fetch_array($max_label_query);
           $max_sql = mysqli_query($conn,"SELECT MAX(LABEL) AS max FROM HW_SUGGESTION_S");
           while($total_result = mysqli_fetch_assoc($max_sql)){
             $s_label =  $total_result["max"] +1;
           }
           if(mysqli_query($conn,"INSERT INTO HW_SUGGESTION_S(U_ID, LABEL, S_NAME, TIME) VALUES('".$U_ID."','".$s_label."','".$S_NAME."','".$TIME."');")){
             $response->result = "SUCCESS";
           }else{
             $response->result = "FAILED";
           }
       }
       else { // 라벨 있을때
           //$s_label = $exist_sql;
           while($total_result = mysqli_fetch_assoc($exist_sql)){
             $s_label = (int)$total_result['LABEL'];
            }
           if(mysqli_query($conn,"INSERT INTO HW_SUGGESTION_S(U_ID, LABEL, S_NAME, TIME) VALUES('".$U_ID."','".$s_label."','".$S_NAME."','".$TIME."');")){
             $response->result = "SUCCESS";
           }else{
             $response->result = "FAILED";
           }
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
