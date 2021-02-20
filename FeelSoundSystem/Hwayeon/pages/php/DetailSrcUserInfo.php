<html>
  <head>
    <meta charset="utf-8">
    <title>DetailUserInfo</title>
    <link rel="stylesheet" href="../css/UserInfo.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
  </head>
  <body style="background-color: #ffc0cb;
    font-family: monospace;
    color: #666666;
    font-weight: bold;
    font-size: 23px;">
    <div style="
      margin-top: 3%;
      margin-bottom: 3%;
      font-size: 18px;
      margin-left: 10%;">
      | 사용자 관리 | 사용자 설정 |
    </div>
      <div
        style="overflow:scroll;
        border-radius: 50px;
        margin: 0 auto;
        width:85%;
        height:92%;
        padding: 5% 5% 5% 5%;
        background-color: #ffffff;">
        <?php
          $U_ID = $_POST['u_id'];
          echo "<h3> < 사용자 아이디 '$U_ID'님의 설정 정보 > </h3>";
          ?>
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
            <th class="set_id" style="width: 15%;">설정 NO.</th>
            <th class="s_name" style="width: 25%;">소리 종류</th>
            <th class="vib_p" style="width: 15%;">진동 패턴</th>
            <th class="vib_s" style="width: 15%;">진동 크기</th>
          </thead>
          <tbody>
            <?php
              $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
              if(mysqli_connect_errno($con))
              {
                echo mysqli_connect_error();
              }
              mysqli_set_charset($con, "utf8");
              $sql="SELECT SET_ID, S_NAME, VIB_P, VIB_S FROM HW_SETTING WHERE U_ID = '$U_ID';";
              $sql_result = mysqli_query($con,$sql);
              if(!$sql_result){
                echo json_encode("err");
              }else{
                $tuple=array();
                $attribute=array();
                while ($attribute = mysqli_fetch_array($sql_result)) {
                  array_push($attribute, array(
                    'set_id'=>$attribute[0],
                    'snd_name'=>$attribute[1],
                    'vib_p'=>$attribute[2],
                    'vib_s'=>$attribute[3]));
                    array_push($tuple, $attribute);
                    echo "<tr>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='set_id'>$attribute[0]</td>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='snd_name'>$attribute[1]</td>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='vib_p'>$attribute[2]</td>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='vib_s'>$attribute[3]</td>";
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
        font-size: 21px;"> 홈 </button>
        <button type="button" name="cancle" class="button" onclick = "location.href='UserInfo.php'"
        style="float: right;
        margin-right: 1%;
        margin-top: 20%;
        width: 8%;
        height: 3%;
        border: none;
        border-radius: 4px;
        background: #666666;
        color:#fff;
        font-size: 21px;">전체 목록</button>

      </div>
  </body>

</html>
