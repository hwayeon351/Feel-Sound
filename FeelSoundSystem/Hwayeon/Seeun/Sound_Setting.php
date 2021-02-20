<?php
  include("dbConn.php");
  $Conf = new JConfig;
  $conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

  $U_ID = $_GET['_id'];
  $S_NUM =$_GET['S_NUM'];
  $S_NAME = $_GET['S_NAME'];
  $VIB_P =$_Get['VIB_P'];
  $VIB_S =$_Get['VIB_S'];

  $statement = mysqli_query($conn, "SELECT * FROM HW_SETTING WHERE U_ID = '".$U_ID."' AND S_NUM = '".$S_NUM."' AND S_NAME = '".$S_NAME."'");

  $response = new StdClass();

  if($U_ID!=''||$S_NUM!=''||$VIB_P!=''){
    if($statement->num_rows){//수정
        if(mysqli_query($conn,"UPDATE HW_SETTING SET VIB_P='".$VIB_P."', VIB_S = '".$VIB_S."' WHERE U_ID='".$U_ID."' AND S_NUM='".$S_NUM."'")){
            $response->result = "SUCCESS";
         }
         else{
            $response->result = "FAILED";
         }
     }
     else{
       if($S_NAME==''){//삭제
         if(mysqli_query($conn,"DELETE FROM HW_SETTING WHERE '".$U_ID."' AND S_NUM ='".$S_NUM."';")){
             $response->result = "SUCCESS";
          }
          else{
             $response->result = "FAILED";
          }

       }
       else{//추가
         if(mysqli_query($conn,"INSERT INTO HW_SETTING(U_ID, S_NUM, S_NAME, VIB_P, VIB_S) VALUES('".$U_ID."','".$S_NUM."','".$S_NAME."','".$VIB_P."','".$VIB_S."');")){
             $response->result = "SUCCESS";
          }
          else{
             $response->result = "FAILED";
          }
       }
     }
   }

  header('Content-Type: application/json');
  echo json_encode($response, JSON_PRETTY_PRINT|JSON_UNESCAPED_UNICODE);

  mysqli_close($conn);

?>
