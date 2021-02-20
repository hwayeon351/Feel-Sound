<html>
  <head>
    <meta charset="utf-8">
    <title>UserInfo</title>
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
      | 사용자 관리 |
    </div>
      <div
        style="overflow:scroll;
        border-radius: 50px;
        margin: 0 auto;
        width:85%;
        height:92%;
        padding: 5% 5% 5% 5%;
        background-color: #ffffff;">
        <h3> < 사용자 목록 > </h3>
        <form action="SearchUser.php" method="post">
          <input type="submit" value="검색" class="search_btn"
            style="float: right;
            margin-right: 1%;
            margin-bottom: 2%;
            width: 8%;
            height: 6%;
            border: none;
            border-radius: 4px;
            background: #666666;
            color:#fff;
            font-size: 21px;">
          <input type="text"
            style="float: right;
            margin-right: 2%;
            margin-bottom: 2%;
            width: 50%;
            height: 6%;
            padding-left: 1%;
            font-size: 20px;
            border: 2px solid #d7d7d7;" name="search_user" class="input_box" placeholder="사용자 아이디를 입력하세요.">
        </form>
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
            <th class="U_id" style="width: 25%;">아이디</th>
            <th class="U_pw" style="width: 25%;">비밀번호</th>
            <th class="U_pw" style="width: 10%;">이름</th>
            <th class="U_num" style="width: 25%;">전화번호</th>
            <th class="U_info">정보</th>
          </thead>
          <tbody>
            <?php
              $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
              if(mysqli_connect_errno($con))
              {
                echo mysqli_connect_error();
              }

              mysqli_set_charset($con, "utf8");
              $sql="SELECT * FROM HW_USER;";
              $sql_result = mysqli_query($con,$sql);
              if(!$sql_result){
                echo json_encode("err");
              } else{
                $tuple=array();
                $attribute=array();
                while($attribute = mysqli_fetch_array($sql_result)){
                  array_push($attribute, array(
                    'u_id'=>$attribute[0],
                    'u_pw'=>$attribute[1],
                    'u_name'=>$attribute[2],
                    'u_phone'=>$attribute[3]));
                    array_push($tuple,$attribute);
                  echo "<tr>";
                  echo "<td style='border: 1px solid #d7d7d7;' name='u_id'>$attribute[0]</td>";
                  echo "<td style='border: 1px solid #d7d7d7;' name='u_pw'>$attribute[1]</td>";
                  echo "<td style='border: 1px solid #d7d7d7;' name='u_name'>$attribute[2]</td>";
                  echo "<td style='border: 1px solid #d7d7d7;' name='u_phone'>$attribute[3]</td>";

                  echo '<td>';
                  echo '<input type="submit" id="more_btn" name="more_btn" class="more_btn" value="더보기"/>';
                  echo '</td>';

                  echo '</tr>';
                }
              }
            ?>

          </tbody>
        </table>
        <form name="more_form" action="DetailUserInfo.php" method="post">
          <input type="hidden" name="tuple_num" id="tuple_num">
        </form>
        <button type="button" name="cancle" class="button" onclick = "location.href='../../Login.php'"
        style="float: right;
        margin-right: 1%;
        margin-top: 20%;
        width: 8%;
        height: 6%;
        border: none;
        border-radius: 4px;
        background: #666666;
        color:#fff;
        font-size: 21px;">뒤로가기</button>
      </div>
      <script type="text/javascript">
        $("input").click(function() {
          if ($(this).hasClass("more_btn")===true) {
            var t_index = $(".more_btn").index($(this));
            document.getElementById('tuple_num').value = t_index;
            document.more_form.submit();
          }
        });
      </script>
  </body>

</html>
