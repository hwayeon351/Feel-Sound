<!DOCTYPE html>
<html lang="en" dir="ltr">
  <?php
    session_start();
    if(!isset($_SESSION['id']))
    {
      header ('Location: ../index.html');
    }
    header('Content-Type: text/html; charset=UTF-8');
  ?>
  <head>
    <meta charset="utf-8">
    <title>MainPage</title>
    <link rel ="stylesheet" href="pages/css/MainPage.css">
  </head>
  <body>
    <!--menu-->
    <div id="menu">
      <ul class="main1">
        <li class="menu_icon">
          <span class="menuBtn"><img src="pages/images/menu_icon1.png"
            width=50px height=50px></span>
          <ul class="main2">
            <li><a href="pages/SendNotice.html">알림 전송</a></li>
            <li class="Analysis">통계 분석
              <ul class="main3">
                <li><a href="pages/php/DetectedSound_day.php">소리 인식</a></li>
                <li><a href="pages/php/UserSettings.php">사용자 설정</a></li>
              </ul>
            </li>
            <li><a href="pages/php/UserInfo.php">사용자 관리</a></li>
            <li><a href="pages/php/Requirement_top.php">사용자 건의사항</a></li>
          </ul>
        </li>
      </ul>
    </div>
    <!--//menu-->
    <!--login-->
    <button type="button" name="logout" class="logout_btn" onclick = "location.href='Logout.php'"
      style="width: 100px;
      height: 28px;
      margin-right: 5%;
      margin-left: 1%;
      float: right;
      font-size: 21px;
      border: none;
      border-radius: 7px;
      background: #666666;
      color:#fff;">로그아웃</button>
    <div style="width: 90%;
      margin-top: 5%;
      padding-top: 1%;
      text-align: right;
      font-size: 21px;
      color:#fff;">
      안녕하세요! master1님.
    </div>

    <!--//login-->
    <!--content-->
    <div id="content">
      <div class="subtitle">
        청각장애인을 위한<br>통합 시스템
      </div>
      <h1 class="title">소리를<br>느끼다</h1>
    </div>
    <!--//comtent-->

  </body>
</html>
