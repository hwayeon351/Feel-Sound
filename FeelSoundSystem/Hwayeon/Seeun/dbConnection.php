<?php
include("dbConn.php");
$Conf = new JConfig;
$conn = mysqli_connect($Conf->host, $Conf->user, $Conf->password, $Conf->db);

echo "MySql 연결 test<br>";

if($conn){
    echo "connect : success<br>";
}
else{
    echo "disconnect : fail<br>";
}

?>
