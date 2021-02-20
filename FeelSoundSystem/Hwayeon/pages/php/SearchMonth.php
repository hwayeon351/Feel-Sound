<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>DetectedSound_month</title>
    <link rel="stylesheet" href="../css/DetectedSound.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker3.standalone.min.css">
    <!-- 차트 링크 -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>

  </head>
  <body>
    <?php
      $datepicker_from = $_POST['datepicker_from'];
      $datepicker_to = $_POST['datepicker_to'];
      $from_date = explode("/",$datepicker_from);
      $from_y = $from_date[0];
      $from_m = $from_date[1];

      $cur_YM = date("Y/m");
      $cur_YMD = date("Y/m/d");
      $cur_Y = date("Y");
      $cur_m = date("m");
      $cur_D = date("d");
      $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
      if(mysqli_connect_errno($con))
      {
        echo mysqli_connect_error();
      }
      mysqli_set_charset($con, "utf8");

      //라인그래프 데이터
      //baby_crying
      $day = [4,9,14,19,24];
      $cnt = 0;
      $baby_records = array();
      $baby_cnt = 1;

      $baby_records[0] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_from."%' AND S_NUM = 0;");
      while ($baby_cnt < 6) {
        $baby_records[$baby_cnt] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE DATE(TIME) = DATE_ADD('".$datepicker_from."', INTERVAL '".$day[$cnt]."' DAY) AND S_NUM = 0");
        $baby_cnt++;
        $cnt++;
      }
      $baby_records[6] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_to."%' AND S_NUM = 0;");
      $baby_cnt = 0;
      $cnt = 0;

      $n_baby = array();

      while($baby_cnt < 7){
        if(!($baby_records[$baby_cnt]->num_rows)){
          $n_baby[$baby_cnt] = 0;
        }
        else{
          while($total_result = mysqli_fetch_assoc($baby_records[$baby_cnt]))
          {
            $n_baby[$baby_cnt] = $total_result['COUNT(*)'];
          }
        }
        $baby_cnt++;
      }

      //car_horn
      $car_records = array();
      $car_cnt = 1;

      $car_records[0] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_from."%' AND S_NUM = 1;");
      while ($car_cnt < 6) {
        $car_records[$car_cnt] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE DATE(TIME) = DATE_ADD('".$datepicker_from."', INTERVAL '".$day[$cnt]."' DAY) AND S_NUM = 1");
        $car_cnt++;
        $cnt++;
      }
      $car_records[6] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_to."%' AND S_NUM = 1;");
      $car_cnt = 0;
      $cnt = 0;

      $n_car= array();

      while($car_cnt < 7){
        if(!($car_records[$car_cnt]->num_rows)){
          $n_car[$car_cnt] = 0;
        }
        else{
          while($total_result = mysqli_fetch_assoc($car_records[$car_cnt]))
          {
            $n_car[$car_cnt] = $total_result['COUNT(*)'];
          }
        }
        $car_cnt++;
      }

      //dog_bark
      $dog_day = array();
      $dog_records = array();
      $dog_cnt = 1;

      $dog_records[0] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_from."%' AND S_NUM = 2;");
      while ($dog_cnt < 6) {
        $dog_records[$dog_cnt] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE DATE(TIME) = DATE_ADD('".$datepicker_from."', INTERVAL '".$day[$cnt]."' DAY) AND S_NUM = 2");
        $dog_cnt++;
        $cnt++;
      }
      $dog_records[6] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_to."%' AND S_NUM = 2;");
      $dog_cnt = 0;
      $cnt = 0;

      $n_dog = array();

      while($dog_cnt < 7){
        if(!($dog_records[$dog_cnt]->num_rows)){
          $n_dog[$dog_cnt] = 0;
        }
        else{
          while($total_result = mysqli_fetch_assoc($dog_records[$dog_cnt]))
          {
            $n_dog[$dog_cnt] = $total_result['COUNT(*)'];
          }
        }
        $dog_cnt++;
      }

      //jack_hammer
      $jack_day = array();
      $jack_records = array();
      $jack_cnt = 1;

      $jack_records[0] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_from."%' AND S_NUM = 3;");
      while ($jack_cnt < 6) {
        $jack_records[$jack_cnt] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE DATE(TIME) = DATE_ADD('".$datepicker_from."', INTERVAL '".$day[$cnt]."' DAY) AND S_NUM = 3");
        $jack_cnt++;
        $cnt++;
      }
      $jack_records[6] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_to."%' AND S_NUM = 3;");
      $jack_cnt = 0;
      $cnt = 0;

      $n_jack = array();

      while($jack_cnt < 7){
        if(!($jack_records[$jack_cnt]->num_rows)){
          $n_jack[$jack_cnt] = 0;
        }
        else{
          while($total_result = mysqli_fetch_assoc($jack_records[$jack_cnt]))
          {
            $n_jack[$jack_cnt] = $total_result['COUNT(*)'];
          }
        }
        $jack_cnt++;
      }

      //siren
      $siren_day = array();
      $siren_records = array();
      $siren_cnt = 1;

      $siren_records[0] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_from."%' AND S_NUM = 4;");
      while ($siren_cnt < 6) {
        $siren_records[$siren_cnt] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE DATE(TIME) = DATE_ADD('".$datepicker_from."', INTERVAL '".$day[$cnt]."' DAY) AND S_NUM = 4");
        $siren_cnt++;
        $cnt++;
      }
      $siren_records[6] = mysqli_query($con,"SELECT COUNT(*) FROM HW_RECORD WHERE TIME LIKE '".$datepicker_to."%' AND S_NUM = 4;");
      $siren_cnt = 0;
      $cnt = 0;

      $n_siren = array();

      while($siren_cnt < 7){
        if(!($siren_records[$siren_cnt]->num_rows)){
          $n_siren[$siren_cnt] = 0;
        }
        else{
          while($total_result = mysqli_fetch_assoc($siren_records[$siren_cnt]))
          {
            $n_siren[$siren_cnt] = $total_result['COUNT(*)'];
          }
        }
        $siren_cnt++;
      }


      //원형그래프 데이터
      $total_records_sql = "SELECT COUNT(*) AS `total` FROM HW_RECORD WHERE TIME LIKE '".$from_y."/".$from_m."%'";
      $baby_records_sql = "SELECT COUNT(*) AS `baby_cry` FROM HW_RECORD WHERE S_NUM=0 AND TIME LIKE '".$from_y."/".$from_m."%'";
      $car_records_sql = "SELECT COUNT(*) AS `car_horn` FROM HW_RECORD WHERE S_NUM=1 AND TIME LIKE '".$from_y."/".$from_m."%'";
      $dog_records_sql = "SELECT COUNT(*) AS `dog_bark` FROM HW_RECORD WHERE S_NUM=2 AND TIME LIKE '".$from_y."/".$from_m."%'";
      $jack_records_sql = "SELECT COUNT(*) AS `jack_hammer` FROM HW_RECORD WHERE S_NUM=3 AND TIME LIKE '".$from_y."/".$from_m."%'";
      $siren_records_sql = "SELECT COUNT(*) AS `siren` FROM HW_RECORD WHERE S_NUM=4 AND TIME LIKE '".$from_y."/".$from_m."%'";


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
        $num_total = 0;
      }
      else{
        while($total_result = mysqli_fetch_assoc($total_sql_result))
        {
          $num_total = (int)$total_result['total'];
        }
      }

      if(!$baby_sql_result){
        $num_baby = 0;
      }
      else{
        while($baby_result = mysqli_fetch_assoc($baby_sql_result))
        {
          $num_baby = (int)$baby_result['baby_cry'];
        }
      }

      if(!$car_sql_result){
        $num_car = 0;
      }
      else{
        while($car_result = mysqli_fetch_assoc($car_sql_result))
        {
          $num_car = (int)$car_result['car_horn'];
        }
      }

      if(!$dog_sql_result){
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
      | 통계 분석 | 소리 인식 | 월  별 |
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
      <!-- datepicker -->
      <form action="SearchMonth.php" method="post">
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
        <input type="text" id="datepicker_to" name="datepicker_to" value="날짜를 선택하세요."
          style="float: right;
          margin-right: 2%;
          margin-bottom: 2%;
          width: 15%;
          height: 3%;
          padding-left: 1%;
          font-size: 20px;
          border: 2px solid #d7d7d7;">
        <div style="float: right;
                  margin-bottom: 2%;
                  width: 3%;
                  height: 3%;
                  font-size: 23px;"> ~ </div>
        <input type="text" id="datepicker_from" name="datepicker_from" value="날짜를 선택하세요."
          style="float: right;
          margin-right: 2%;
          margin-bottom: 2%;
          width: 15%;
          height: 3%;
          padding-left: 1%;
          font-size: 20px;
          border: 2px solid #d7d7d7;">
      </form>
        <h1 class="title_h1"> <? echo $from_y; ?>년 <? echo $from_m; ?>월 데이터 </h1>
        <h3 class="graph_title">
          < 날짜별 소리 인식 횟수 >
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
    <!-- datepicker -->
    <script src="https://ajax.googleapis.com/ajax/libs/jqery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/locales/bootstrap-datepicker.kr.min.js"></script>
    <script type="text/javascript">
        $('#datepicker_from').datepicker({
          format: "yyyy/mm/dd",
          language : "kr"
        });
        $('#datepicker_to').datepicker({
          format: "yyyy/mm/dd",
          language : "kr"
        });

    </script>

    <!-- 부트스트랩 -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <!-- 차트 -->
    <!-- 차트 -->
    <script>
      // chart colors
      const colors = ['red','yellow','blue','#008000','#6A287E','#6c757d'];
      // const datas = [589, 445, ...] 선언하고 아래서 datas 변수만 써줘도 된다.
      var ctx = document.getElementById('myChart');
      var chartData = {
              labels: ["<? echo $datepicker_from; ?>", "<? echo $from_y; ?>/<? echo $from_m; ?>/05", "<? echo $from_y; ?>/<? echo $from_m; ?>/10", "<? echo $from_y; ?>/<? echo $from_m; ?>/15",
              "<? echo $from_y; ?>/<? echo $from_m; ?>/20", "<? echo $from_y; ?>/<? echo $from_m; ?>/25", "<? echo $datepicker_to; ?>"],
              datasets: [{ data: [ <? echo $n_baby[0]; ?>, <? echo $n_baby[1]; ?>, <? echo $n_baby[2]; ?>, <? echo $n_baby[3]; ?>, <? echo $n_baby[4]; ?>, <? echo $n_baby[5]; ?>,
                <? echo $n_baby[6]; ?>],
              backgroundColor: 'transparent',
              borderColor: colors[0],
              borderWidth: 3,
              pointBackgroundColor: colors[0]
            },
            {
              data: [<? echo $n_car[0]; ?>, <? echo $n_car[1]; ?>, <? echo $n_car[2]; ?>, <? echo $n_car[3]; ?>, <? echo $n_car[4]; ?>, <? echo $n_car[5]; ?>,
                <? echo $n_car[6]; ?>],
              backgroundColor: 'transparent',
              borderColor: colors[1],
              borderWidth: 3,
              pointBackgroundColor: colors[1]
            },
            {
              data: [<? echo $n_dog[0]; ?>, <? echo $n_dog[1]; ?>, <? echo $n_dog[2]; ?>, <? echo $n_dog[3]; ?>, <? echo $n_dog[4]; ?>, <? echo $n_dog[5]; ?>,
                <? echo $n_dog[6]; ?>],
              backgroundColor: 'transparent',
              borderColor: colors[2],
              borderWidth: 3,
              pointBackgroundColor: colors[2]
            },
            {
              data: [<? echo $n_jack[0]; ?>, <? echo $n_jack[1]; ?>, <? echo $n_jack[2]; ?>, <? echo $n_jack[3]; ?>, <? echo $n_jack[4]; ?>, <? echo $n_jack[5]; ?>
              , <? echo $n_jack[6]; ?>],
              backgroundColor: 'transparent',
              borderColor: colors[3],
              borderWidth: 3,
              pointBackgroundColor: colors[3]
            },
            {
              data: [<? echo $n_siren[0]; ?>, <? echo $n_siren[1]; ?>, <? echo $n_siren[2]; ?>, <? echo $n_siren[3]; ?>, <? echo $n_siren[4]; ?>, <? echo $n_siren[5]; ?>,
              <? echo $n_siren[6]; ?>],
              backgroundColor: 'transparent',
              borderColor: colors[4],
              borderWidth: 3,
              pointBackgroundColor: colors[4]
            }
          ]
        };
        var myChart = new Chart(ctx, {
          // 챠트 종류를 선택
          type: 'line',
          // 챠트를 그릴 데이타
          data: chartData,
          // 옵션
          options: {
            legend:{
              display: false
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
