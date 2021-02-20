<html>
  <head>
    <link rel="stylesheet" href="../css/SendNotice.css">
    <title>SendAnswer</title>
    <meta charset="utf-8">
  </head>
  <body>
    <div id="function">
      | 사용자 건의 사항 | 기  타 | 답변 하기 |
    </div>
    <form action="SendNotice.php" method="post">
      <div class="Content">
        <?php
          $con=mysqli_connect("localhost", "cha6511", "qa842527!!", "cha6511");
          if(mysqli_connect_errno($con))
          {
            echo mysqli_connect_error();
          }

          mysqli_set_charset($con, "utf8");

          $sql="SELECT U_ID, CONTENT FROM HW_SUGGESTION;";
          $sql_result = mysqli_query($con,$sql);
          if(!$sql_result){
            echo json_encode("err");
          }
          else{
            $SgtArr=array();
            $attribute=array();
            while($attribute = mysqli_fetch_array($sql_result)){
              array_push($attribute, array(
                'u_id'=>$attribute[0],
                'content'=>$attribute[1]));
              array_push($SgtArr,$attribute);
            }
          }
          $tuple_index = $_POST['tuple_num'];
          $U_ID = $SgtArr[$tuple_index][0];
          $sgt_content = $SgtArr[$tuple_index][1];

         echo "<div class='User_sgt' style='margin-bottom: 1%;'>
              건의 내용: $sgt_content <br>
              사용자 아이디: $U_ID  <br><br>
              답변:
              </div>"
         ?>
        <textarea class="n_content"
          autofocus required
          placeholder="답변을 입력하세요."
          maxlength="1000"
          rows="30"
          name="n_content"></textarea>
      </div>
      <div class="btn_set">
            <input type="submit" name="n_send" class="button" value="전송"/>
            <button type="button" name="cancle" class="button" onclick = "location.href='../../Login.php'">취소</button>
      </div>
    </form>
  </body>
</html>
