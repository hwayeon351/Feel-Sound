<?php
  function dbconn(){
  $host_name="localhost";
  $db_user_id ="master1";
  $db_name="master1";
  $db_pw="master123";
  $connect=@mysql_connect($host_name, $db_user_id, $db_pw);
  mysql_query("set names utf8", $connect);
  mysql_select_db($db_name, $connect);
  if(!$connect)die("연결에 실패했습니다.".mysql_error());
  return $connect;
  }

  //에러메세지 출력
  function Error($msg)
  {
    echo"
    <script>
    window.alert('$msg');
    history.back(1);
    </script>
    ";
    exit; //위 에러 메세지만 띄우기
  }
?>
