package com.course.feelsound;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {
    TextView my_id0, my_pass0, my_name0, my_contact0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        setTitle("마이페이지");
        //사용자 정보
        Intent intent = getIntent();
        String id = intent.getExtras().getString("id");
        String pass = intent.getExtras().getString("pass");
        String name = intent.getExtras().getString("name");
        String contact = intent.getExtras().getString("contact");

        my_id0 = (TextView)findViewById(R.id.my_id);
        my_id0.setText("아이디 " + id);
        my_pass0 = (TextView)findViewById(R.id.my_pass);
        my_pass0.setText("비밀번호 " + pass);
        my_name0 = (TextView)findViewById(R.id.my_name);
        my_name0.setText("이름 " + name);
        my_contact0 = (TextView)findViewById(R.id.my_contact);
        my_contact0.setText("연락처 " + contact);

    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}