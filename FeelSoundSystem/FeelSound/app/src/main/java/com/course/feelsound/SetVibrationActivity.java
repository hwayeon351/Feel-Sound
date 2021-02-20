package com.course.feelsound;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetVibrationActivity extends AppCompatActivity {
    RadioButton v_low,v_strong;
    RadioButton v_one,v_two;
    String id,s_num;
    private Messenger mServiceMessenger = null;
    RetrofitAPI api;
    String res="";
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_vibration_layout);
        Intent intent = getIntent();
        s_num= intent.getExtras().getString("s_num");
        id = intent.getExtras().getString("id");

        setTitle("진동 설정");

        v_low = (RadioButton)findViewById(R.id.v_low);
        v_strong = (RadioButton)findViewById(R.id.v_strong);
        v_one = (RadioButton)findViewById(R.id.v_one);
        v_two = (RadioButton)findViewById(R.id.v_two);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_btn:
                saveBtnOnClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    public void saveBtnOnClicked() {
        int vib_p = 2;
        int vib_s = 2;
        String s_name = "";
        if (v_low.isChecked()) {
            vib_s = 1;
        }
        if (v_strong.isChecked()) {
            vib_s = 2;
        }
        if (v_one.isChecked()) {
            vib_p = 1;
        }
        if (v_two.isChecked()) {
            vib_p = 2;
        }
        switch (s_num) {
            case "0":
                s_name = "아기 울음 소리";
                break;
            case "1":
                s_name = "차 경적 소리";
                break;
            case "2":
                s_name = "개 짖는 소리";
                break;
            case "3":
                s_name = "사이렌 소리";
                break;
            case "4":
                s_name = "굴착기 소리";
                break;
            default:
                s_name = "";
                break;
        }
        api = RetrofitInit.getRetrofit();
        Call<JsonObject> s_setting = api.s_setting(id, s_name, s_num, String.valueOf(vib_p), String.valueOf(vib_s));
        //enqueue -> 보내기
        s_setting.enqueue(new Callback<JsonObject>() {
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
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {//php까지 도달이 안 된 상태
                t.printStackTrace();
            }
        });
        if (res.equals("FAILED")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SetVibrationActivity.this);
            dialog = builder.setMessage("다시 시도해주세요.")
                    .setPositiveButton("확인", null)
                    .create();
            dialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(SetVibrationActivity.this);
            dialog = builder.setMessage("전송되었습니다.")
                    .setNegativeButton("확인", null)
                    .create();
            dialog.show();
            finish();
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}
