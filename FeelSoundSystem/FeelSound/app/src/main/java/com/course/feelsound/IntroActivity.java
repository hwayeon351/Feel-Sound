package com.course.feelsound;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class IntroActivity extends Activity {
    Handler handler = new Handler();
    Runnable r = new Runnable() {
        @Override
        public void run() {
            // 3초뒤에 다음화면으로 넘어가기 Handler 사용
            Log.i("TEST", "Intro : Start Service");
            startService(new Intent(getApplicationContext(), BluetoothDataService.class));
            Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
            startActivity(intent); // 다음화면으로 넘어가기
            finish(); // Activity 화면 제거
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);
        String readMessage = "1 2019-12-19 11:15:52  Baby Crying";
        String s_type = readMessage.substring(0,1);
        String time = readMessage.substring(2,21);
        String s_id = readMessage.substring(23);

        Log.d("TEEEE", s_type + "       " + time + "    " + s_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 다시 화면에 들어어왔을 때 예약
        handler.postDelayed(r, 3000); // 3초 뒤에 Runnable 객체 수행
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 화면을 벗어나면, handler 에 예약해놓은 작업을 취소
        handler.removeCallbacks(r); // 예약 취소
    }



}
