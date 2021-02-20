package com.course.feelsound;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerServiceActivity extends AppCompatActivity {
    RetrofitAPI api;
    String res;
    String id,pass,name,contact;

    private EditText et_suggestion, et_want_sound;
    private Button btn_snt,btn_snt1;
    private AlertDialog dialog;
    private boolean validate = false;
    Button set_snd_btn, snd_det_btn, customer_btn;

    //time
    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    // nowDate 변수에 값을 저장한다.
    String time = sdfNow.format(date);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_layout);
        setTitle("고객센터");

        //사용자 정보
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        pass = intent.getExtras().getString("pass");
        name = intent.getExtras().getString("name");
        contact = intent.getExtras().getString("contact");

        //button
        set_snd_btn = findViewById(R.id.record_btn);
        snd_det_btn = findViewById(R.id.set_btn);

        Button.OnClickListener onClickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.record_btn:/*
                        Intent record_intent = new Intent(CustomerServiceActivity.this,RecordActivity.class);
                        record_intent.putExtra("id",id);
                        record_intent.putExtra("pass",pass);
                        record_intent.putExtra("name", name);
                        record_intent.putExtra("contact", contact);
                        startActivity(record_intent);*/
                        finish();
                        break;
                    case R.id.set_btn:
                        finish();
                        Intent danger_intent = new Intent(CustomerServiceActivity.this,SetDangerActivity.class);
                        danger_intent.putExtra("id",id);
                        danger_intent.putExtra("pass",pass);
                        danger_intent.putExtra("name", name);
                        danger_intent.putExtra("contact", contact);
                        startActivity(danger_intent);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            }
        };
        set_snd_btn.setOnClickListener(onClickListener);
        snd_det_btn.setOnClickListener(onClickListener);


        //아이디 값 찾아주기
        et_suggestion = findViewById(R.id.et_suggestion);
        et_want_sound = findViewById(R.id.et_want_sound);

        btn_snt = findViewById(R.id.btn_snt);
        btn_snt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_suggestion.getText().toString();
                if (content.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                    dialog = builder.setMessage("빈칸을 채워주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else {
                    api = RetrofitInit.getRetrofit();
                    Call<JsonObject> customer = api.customer(id, content, time);
                    //enqueue -> 보내기
                    customer.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("res", response.body().toString());
                            JsonObject serverRes = response.body();//서버에서 내려온 body는 jsonObject 형태
                            try {
                                res = serverRes.get("result").getAsString();
                            } catch (Exception e) {
                                e.printStackTrace();
                                res = "error";
                            }
                            if (res.equals("SUCCESS")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                                dialog = builder.setMessage("전송되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                                dialog = builder.setMessage("다시 시도해주세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {//php까지 도달이 안 된 상태
                            t.printStackTrace();
                        }
                    });

                }
            }
        });

        btn_snt1 = findViewById(R.id.btn_snt1);
        btn_snt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s_name = et_want_sound.getText().toString();
                if (s_name.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                    dialog = builder.setMessage("빈칸을 채워주세요")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                } else {
                    api = RetrofitInit.getRetrofit();
                    Call<JsonObject> customer_s = api.customer_s(id,s_name, time);
                    //enqueue -> 보내기
                    customer_s.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("res", response.body().toString());
                            JsonObject serverRes = response.body();//서버에서 내려온 body는 jsonObject 형태
                            try {
                                res = serverRes.get("result").getAsString();
                            } catch (Exception e) {
                                e.printStackTrace();
                                res = "error";
                            }
                            if (res.equals("SUCCESS")) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                                dialog = builder.setMessage("전송되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CustomerServiceActivity.this);
                                dialog = builder.setMessage("다시 시도해주세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {//php까지 도달이 안 된 상태
                            t.printStackTrace();
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.my_btn:
                Intent my_intent = new Intent(CustomerServiceActivity.this, MyPageActivity.class);
                my_intent.putExtra("id", id);
                my_intent.putExtra("pass", pass);
                my_intent.putExtra("name", name);
                my_intent.putExtra("contact", contact);
                startActivity(my_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}

