<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>UserSettings</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/UserSettings.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <?php
      $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
      if(mysqli_connect_errno($con))
      {
        echo mysqli_connect_error();
      }
      mysqli_set_charset($con, "utf8");
      $total_records_sql = "SELECT COUNT(*) AS `total` FROM HW_SETTING";
      $baby_records_sql = "SELECT COUNT(*) AS `baby_cry` FROM HW_SETTING WHERE S_NUM=0";
      $car_records_sql = "SELECT COUNT(*) AS `car_horn` FROM HW_SETTING WHERE S_NUM=1 ";
      $dog_records_sql = "SELECT COUNT(*) AS `dog_bark` FROM HW_SETTING WHERE S_NUM=2 ";
      $jack_records_sql = "SELECT COUNT(*) AS `jack_hammer` FROM HW_SETTING WHERE S_NUM=3 ";
      $siren_records_sql = "SELECT COUNT(*) AS `siren` FROM HW_SETTING WHERE S_NUM=4 ";

      $total_sql_result = mysqli_query($con, $total_records_sql);
      $baby_sql_result = mysqli_query($con, $baby_records_sql);
      $car_sql_result = mysqli_query($con, $car_records_sql);
      $dog_sql_result = mysqli_query($con, $dog_records_sql);
      $siren_sql_result = mysqli_query($con, $siren_records_sql);
      $jack_sql_result = mysqli_query($con, $jack_records_sql);

      $num_total;
      $num_baby;
      $num_car;
      $num_dog;
      $num_jack;
      $num_siren;

      $ratio_baby;
      $ratio_car;
      $ratio_dog;
      $ratio_jack;
      $ratio_siren;

      if(!$total_sql_result){
        echo json_encode($total_sql_result);
        $num_total = 0;
      }
      else{
        while($total_result = mysqli_fetch_assoc($total_sql_result))
        {
          $num_total = (int)$total_result['total'];
        }
      }

      if(!$baby_sql_result){
        echo json_encode($baby_sql_result);
        $num_baby = 0;
      }
      else{
        while($baby_result = mysqli_fetch_assoc($baby_sql_result))
        {
          $num_baby = (int)$baby_result['baby_cry'];
          $ratio_baby = $num_baby/$num_total*100;
        }
      }

      if(!$car_sql_result){
        echo json_encode($car_sql_result);
        $num_car = 0;
      }
      else{
        while($car_result = mysqli_fetch_assoc($car_sql_result))
        {
          $num_car = (int)$car_result['car_horn'];
          $ratio_car = $num_car/$num_total*100;
        }
      }

      if(!$dog_sql_result){
        echo json_encode($dog_sql_result);
        $num_dog = 0;
      }
      else{
        while($dog_result = mysqli_fetch_assoc($dog_sql_result))
        {
          $num_dog = (int)$dog_result['dog_bark'];
          $ratio_dog = $num_dog/$num_total*100;
        }
      }

      if(!$jack_sql_result){
        echo json_encode($jack_sql_result);
        $num_jack = 0;
      }
      else{
        while($jack_result = mysqli_fetch_assoc($jack_sql_result))
        {
          $num_jack = (int)$jack_result['jack_hammer'];
          $ratio_jack = $num_jack/$num_total*100;
        }
      }

      if(!$siren_sql_result){
        echo json_encode($siren_sql_result);
        $num_siren = 0;
      }
      else{
        while($siren_result = mysqli_fetch_assoc($siren_sql_result))
        {
          $num_siren = (int)$siren_result['siren'];
          $ratio_siren = $num_siren/$num_total*100;
        }
      }

    ?>
    <div id="function">
      | 통계 분석 | 사용자 설정 |
    </div>
    <div class="box">
      <h2>전체 사용자가 설정한 소리</h2>
      <?php
        echo "<div class='container'>
                <div class='progress'>
                  <div class='progress-bar progress-bar-success' role='progressbar' aria-valuenow= '$ratio_baby' aria-valuemin='0' aria-valuemax='100' style='width:$ratio_baby%'>
                    $ratio_baby% 아기 울음 소리
                  </div>
                </div>
                <div class='progress'>
                  <div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='$ratio_car' aria-valuemin='0' aria-valuemax='100' style='width:$ratio_car%'>
                    $ratio_car% 차 경적 소리
                  </div>
                </div>
                <div class='progress'>
                  <div class='progress-bar progress-bar-warning' role='progressbar' aria-valuenow='$ratio_dog' aria-valuemin='0' aria-valuemax='100' style='width:$ratio_dog%'>
                    $ratio_dog% 개 짖는 소리
                  </div>
                </div>
                <div class='progress'>
                  <div class='progress-bar progress-bar-danger' role='progressbar' aria-valuenow='$ratio_jack' aria-valuemin='0' aria-valuemax='100' style='width:$ratio_jack%'>
                    $ratio_jack% 굴착기 소리
                  </div>
                </div>
                <div class='progress'>
                  <div class='progress-bar progress-bar-info' role='progressbar' aria-valuenow='$ratio_siren' aria-valuemin='0' aria-valuemax='100' style='width:$ratio_siren%'>
                    $ratio_siren% 사이렌 소리
                  </div>
                </div>
              </div>";

       ?>


      <button type="button" name="cancle" class="button" onclick = "location.href='../../Login.php'"
        style="float: right;
        margin-right: 1%;
        margin-top: 20%;
        width: 8%;
        height: 3%;
        border: none;
        border-radius: 4px;
        background: #666666;
        color:#fff;
        font-size: 21px;">뒤로가기</button>
    </div>

  </body>

</html>
