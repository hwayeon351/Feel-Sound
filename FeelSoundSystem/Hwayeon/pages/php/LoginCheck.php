<?php
  header('Content-Type: text/html; charset=UTF-8');
  session_start();
  $get_id = $_POST['id'];
  $get_pw = $_POST['pw'];
  $id = "master1";
  $pw = "master123";

  if ($get_id !== $id && $get_pw === $pw) {
    echo '<script language="javascript">alert("아이디가 일치하지 않습니다.");</script>';
    echo '<script language="javascript">history.back();</script>';
    echo '<script language="javascript">
      function setFocusToTextBox(){
        document.getElementById("id").focus();
      }</script>';
  }
  elseif ($get_pw !== $pw && $get_id === $id) {
    echo '<script language="javascript">alert("비밀번호가 일치하지 않습니다.");</script>';
    echo '<script language="javascript">history.back();</script>';
    echo '<script language="javascript">
      function setFocusToTextBox(){
        document.getElementById("pw").focus();
      }</script>';  }
  elseif ($get_id === $id && $get_pw === $pw){
    $_SESSION['id']=$id;
    if(isset($_SESSION['id']))
    {
      header('Location: ../../Login.php');
    }
    else{
      echo "세션 저장 실패";
    }
  }
  else {
    echo '<script language="javascript">alert("아이디 또는 비밀번호를 확인하세요.");</script>';
    echo '<script language="javascript">history.back();</script>';
    //echo '<script language="javascript">document.getElementById("id").focus();</script>'
  }

 ?>
