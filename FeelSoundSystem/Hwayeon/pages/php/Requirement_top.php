<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>UserRequirement_top</title>
    <link rel="stylesheet" href="../css/UserRequirement.css">
  </head>
  <body>
    <div id="function">
      | 사용자 건의사항 | T O P   1 0 |
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
      <button type="button" name="cancle" class="button" onclick = "location.href='Requirement_sound.php'"
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
      <h3> < 사용자 건의 소리 종류 TOP 10 > </h3>
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
              <th class="U_id" style="width: 10%;">순   위</th>
              <th class="U_pw" style="width: 20%;">소 리   종 류</th>
              <th class="U_pw" style="width: 15%;">건 의   수</th>
            </thead>
            <tbody>
              <?php
                $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
                if(mysqli_connect_errno($con))
                {
                  echo mysqli_connect_error();
                }

                mysqli_set_charset($con, "utf8");

                $sql="SELECT * FROM (SELECT S_NAME, COUNT(*) AS COUNT FROM HW_SUGGESTION_S GROUP BY LABEL) RANKING ORDER BY COUNT DESC LIMIT 10";
                $sql_result = mysqli_query($con,$sql);
                if(!$sql_result){
                  echo json_encode("err");
                } else{
                  $tuple=array();
                  $attribute=array();
                  $rank = 0;
                while($attribute = mysqli_fetch_array($sql_result)){
                  array_push($attribute, array(
                    's_name'=>$attribute[0],
                    'cnt'=>$attribute[1],));
                    array_push($tuple,$attribute);
                    echo "<tr>";
                    ++$rank;
                    echo "<td style='border: 1px solid #d7d7d7;' name='rank'> NO.$rank </td>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='s_name'>$attribute[0]</td>";
                    echo "<td style='border: 1px solid #d7d7d7;' name='cnt'>$attribute[1]</td>";
                    echo '</tr>';
                  }
                }
              ?>

            </tbody>
          </table>
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
    </div>
  </body>

</html>
