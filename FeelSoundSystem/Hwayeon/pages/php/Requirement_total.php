<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>UserRequirement_total</title>
    <link rel="stylesheet" href="../css/UserRequirement.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
  </head>
  <body>
    <div id="function">
      | 사용자 건의사항 |  소 리  종 류  |
    </div>
    <div class="TapBox">
      <button type="button" name="cancle" class="button" onclick = "location.href='Requirement_top.php'"
        style="float: left;
        margin-left: 7%;
        width: 8%;
        font-family: monospace;
        height: 3%;
        border: none;
        border-radius: 4px;
        background: #666666;
        color:#fff;
        font-size: 25px;">TOP 10</button>
      <button type="button" name="cancle" class="button" onclick = "location.href='Requirement_total.php'"
          style="float: left;
          margin-left: 5px;
          width: 8%;
          height: 3%;
          font-family: monospace;
          border: none;
          border-radius: 4px;
          background: #666666;
          color:#fff;
          font-size: 25px;">소리 종류</button>
      <button type="button" name="cancle" class="button" onclick = "location.href='Requirement_etc.php'"
            style="float: left;
            margin-left: 5px;
            width: 8%;
            height: 3%;
            font-family: monospace;
            border: none;
            border-radius: 4px;
            background: #666666;
            color:#fff;
            font-size: 25px;">기  타</button>

    </div>
    <div class="box">
      <h3> < 전체 데이터 > </h1>
        <div>
          <table class="Table" border="1"
            style="margin-left: 1%;
            margin-top: 3%;
            width: 98%;
            max-height: 95%;
            font-weight: normal;
            border: 2px solid #666666;
            font-size: 20px;">
            <thead class="T_header"
              style="font-weight: bold;
              background-color: #d7d7d7;
              font-size: 21px;">
              <th class="g_id" style="width: 10%;"> N O . </th>
              <th class="u_id" style="width: 20%;"> 사용자 아이디 </th>
              <th class="label" style="width: 15%;"> 라 벨 </th>
              <th class="s_name" style="width: 15%;"> 소리 종류 </th>
              <th class="time" style="width: 15%;"> 날 짜 / 시 간 </th>
            </thead>
            <tbody>
              <?php
                $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
                if(mysqli_connect_errno($con))
                {
                  echo mysqli_connect_error();
                }

                mysqli_set_charset($con, "utf8");

                $sql="SELECT * FROM HW_SUGGESTION_S";
                $sql_result = mysqli_query($con,$sql);
                if(!$sql_result){
                  echo json_encode("THERE IS NO SUGGESTION");
                } else{
                  $tuple=array();
                  $attribute=array();
                  while($attribute = mysqli_fetch_array($sql_result)){
                    array_push($attribute, array(
                      'g_id'=>$attribute[0],
                      'u_id'=>$attribute[1],
                      'label'=>$attribute[2],
                      's_name'=>$attribute[3],
                      'time'=>$attribute[4]));
                      array_push($tuple, $attribute);
                      echo "<tr>";
                      echo "<td style='border: 1px solid #d7d7d7;' name='g_id'>$attribute[0]</td>";
                      echo "<td style='border: 1px solid #d7d7d7;' name='u_id'>$attribute[1]</td>";
                      echo "<td style='border: 1px solid #d7d7d7;' name='label'>$attribute[2]</td>";
                      echo "<td style='border: 1px solid #d7d7d7;' name='s_name'>$attribute[3]</td>";
                      echo "<td style='border: 1px solid #d7d7d7;' name='time'>$attribute[4]</td>";
                      echo '</tr>';
                  }
                }
              ?>
            </tbody>
          </table>
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
    </div>
  </body>

</html>
