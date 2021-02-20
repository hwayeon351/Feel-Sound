<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>DetectedSound_day</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.standalone.min.css">
    <!-- 차트 링크 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
    <link rel="stylesheet" href="../css/DetectedSound.css">
  </head>
  <body>
    <?php
      $daily = array('일요일','월요일','화요일','수요일','목요일','금요일','토요일');
      $weekday = $daily[date('w')];

      $cur_YMD = $_POST['datepicker'];

      $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
      if(mysqli_connect_errno($con))
      {
        echo mysqli_connect_error();
      }
      mysqli_set_charset($con, "utf8");
      $total_records_sql = "SELECT COUNT(*) AS `total` FROM HW_RECORD WHERE TIME LIKE '".$cur_YMD."%'";
      $baby_records_sql = "SELECT COUNT(*) AS `baby_cry` FROM HW_RECORD WHERE S_NUM=0 AND TIME LIKE '".$cur_YMD."%'";
      $car_records_sql = "SELECT COUNT(*) AS `car_horn` FROM HW_RECORD WHERE S_NUM=1 AND TIME LIKE '".$cur_YMD."%'";
      $dog_records_sql = "SELECT COUNT(*) AS `dog_bark` FROM HW_RECORD WHERE S_NUM=2 AND TIME LIKE '".$cur_YMD."%'";
      $jack_records_sql = "SELECT COUNT(*) AS `jack_hammer` FROM HW_RECORD WHERE S_NUM=3 AND TIME LIKE '".$cur_YMD."%'";
      $siren_records_sql = "SELECT COUNT(*) AS `siren` FROM HW_RECORD WHERE S_NUM=4 AND TIME LIKE '".$cur_YMD."%'";


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

      if(!$total_sql_result){
        echo json_encode("err in COUNT num of total records");
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
        }
      }

    ?>
    <div id="function">
      | 통계 분석 | 소리 인식 | 일  별 |
    </div>
    <div class="TapBox">
      <button type="button" name="cancle" class="button" onclick = "location.href='DetectedSound_day.php'"
        style="float: left;
        margin-left: 7%;
        width: 8%;
        height: 3%;
        border: none;
        border-radius: 4px;
        background: #666666;
        color:#fff;
        font-size: 25px;">일</button>
        <button type="button" name="cancle" class="button" onclick = "location.href='DetectedSound_week.php'"
          style="float: left;
          margin-left: 5px;
          width: 8%;
          height: 3%;
          border: none;
          border-radius: 4px;
          background: #666666;
          color:#fff;
          font-size: 25px;">주</button>
          <button type="button" name="cancle" class="button" onclick = "location.href='DetectedSound_month.php'"
            style="float: left;
            margin-left: 5px;
            width: 8%;
            height: 3%;
            border: none;
            border-radius: 4px;
            background: #666666;
            color:#fff;
            font-size: 25px;">월</button>
    </div>
    <div class="box">
      <form action="SearchDay.php" method="post">
        <input type="submit" value="검색" class="search_btn"
          style="float: right;
          margin-right: 1%;
          margin-bottom: 2%;
          width: 8%;
          height: 3%;
          border: none;
          border-radius: 4px;
          background: #666666;
          color:#fff;
          font-size: 21px;">
        <input type="text" id="datepicker" name="datepicker" value="날짜를 선택하세요."
          style="float: right;
          margin-right: 2%;
          margin-bottom: 2%;
          width: 15%;
          height: 3%;
          padding-left: 1%;
          font-size: 20px;
          border: 2px solid #d7d7d7;">
      </form>
        <h1 class="title_h1"> <?echo $cur_YMD ?></h1>
        <h3 class="graph_title">
          < 누적 소리 인식 횟수 >
        </h3>
        <div class="total">
          Total: <? echo $num_total; ?>
        </div>
        <div class="container1"><canvas id="myChart"></canvas> </div>
        <div class="container">
            <div class="row my-3">
              <div class="col-12">
                <h3 class="graph_title">
                  < 누적 소리 인식 퍼센트 >
                </h3>
              </div>
            </div>
            <div class="row my-2">
              <div class="col-lg-6">
                <div class="card">
                  <div class="card-body">
                    <canvas id="myChart1"></canvas>
                  </div>
                  <div class="card-footer text-center text-dark">
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="card">
                  <div class="card-body">
                    <canvas id="myChart2"></canvas>
                  </div>
                  <div class="card card-body text-center bg-primary">
                  </div>
                </div>
              </div>
            </div>
          </div>
          <button type="button" name="cancle" class="button" onclick = "location.href='../../Login.php'"
            style="float: right;
            margin-right: 1%;
            margin-top: 10%;
            width: 8%;
            height: 3%;
            border: none;
            border-radius: 4px;
            background: #666666;
            color:#fff;
            font-size: 21px;">뒤로가기</button>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jqery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.kr.min.js"></script>
    <script type="text/javascript">
        $('#datepicker').datepicker({
          format: "yyyy/mm/dd",
          language : "kr"
        });
    </script>

    <!-- 부트스트랩 -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- 차트 -->
    <script>
          var ctx = document.getElementById('myChart');
          var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
              labels: ['아기 울음 소리', '차 경적 소리', '개 짖는 소리', '굴착기 소리', '사이렌 소리'],
              datasets: [{
                label: '# of Total',
                data: [<? echo $num_baby; ?>, <? echo $num_car; ?>, <? echo $num_dog; ?>, <? echo $num_jack; ?>, <? echo $num_siren; ?>],
                backgroundColor: [
                  'rgba(255, 159, 64, 0.2)',
                  'rgba(54, 162, 235, 0.2)',
                  'rgba(255, 206, 86, 0.2)',
                  'rgba(75, 192, 192, 0.2)',
                  'rgba(153, 102, 255, 0.2)'
                ],
                borderColor: [
                  'rgba(255, 159, 64, 1)',
                  'rgba(54, 162, 235, 1)',
                  'rgba(255, 206, 86, 1)',
                  'rgba(75, 192, 192, 1)',
                  'rgba(153, 102, 255, 1)'
                ],
                borderWidth: 1
              }]
            },
            options: {
              scales: {
                yAxes: [{
                  ticks: {
                    beginAtZero: true
                  }
                }]
              }
            }
          });

          <?php
            echo "data = { datasets: [{ backgroundColor: ['red','yellow','blue', 'green', 'purple'], data: [$num_baby, $num_car, $num_dog, $num_jack, $num_siren] }],
            labels: [' 아기 울음 소리',' 차 경적 소리',' 개 짖는 소리', ' 굴착기 소리', ' 사이렌 소리'] };
            var ctx1 = document.getElementById('myChart1');
            var myPieChart = new Chart(ctx1, { type: 'pie', data: data, options: {} });";
            ?>
    </script>




  </body>

</html>
