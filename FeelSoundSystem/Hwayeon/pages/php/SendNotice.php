<?php
header('Content-Type: text/html; charset=UTF-8');
// API access key from Google API's Console
define('API_ACCESS_KEY', 'AAAAZtYtUdc:APA91bESgFfOy6QxefPNdaGNevwjx1oI1xPbmdtv7PXqjUa1quweUkyZwbqnAhcl5Uh8iTKNcs7bS1UFfYAe3R0PBOLqRiKQbyHe3kjae34sehwDozrjOS1ZEJlRDucfQ_W3EeUOkWMX');

$con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
if(mysqli_connect_errno($con))
{
  echo mysqli_connect_error();
}

mysqli_set_charset($con, "utf8");

$sql = "SELECT TOKEN FROM USER_INFO";
$result = mysqli_query($con,$sql);
$tokens = array();
if(mysqli_num_rows($result) > 0){
  while($row = mysqli_fetch_assoc($result)){
      array_push($tokens, $row['TOKEN']);
    }
}
mysqli_close($con);

function send_notification ($tokens, $message){
  $fields = array(
      'registration_ids'  => $tokens,
      'data'              => $message
    );

  $headers = array(
      'Authorization: key=' . API_ACCESS_KEY,
      'Content-Type: application/json'
    );

  $ch = curl_init();
  curl_setopt($ch, CURLOPT_URL, 'https://fcm.googleapis.com/fcm/send' );
  curl_setopt($ch, CURLOPT_POST, true );
  curl_setopt($ch, CURLOPT_HTTPHEADER, $headers );
  curl_setopt($ch, CURLOPT_RETURNTRANSFER, true );
  curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);
  curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false );
  curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
  $curl_result = curl_exec($ch);
  if ($curl_result === FALSE) {
         die('Curl failed: ' . curl_error($ch));
     }
  curl_close( $ch );
  return $curl_result;
  }

  $n_content = $_POST['n_content'];
  $message = array("message" => $n_content);
  $message_status = send_notification($tokens, $message);
  echo '<script type="text/javascript">alert("알림이 전송되었습니다.");</script>';
  echo("<script>location.href='../../Login.php';</script>");
?>
