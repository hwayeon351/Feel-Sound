package com.course.feelsound;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {
    RetrofitAPI api;
    String res;
    String user;

    private EditText et_id, et_pass;
    private Button btn_login,btn_register;


    private AlertDialog dialog;
    boolean success= false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setTitle("로그인");

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);

        //회원가입
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(LogInActivity.this,JoinActivity.class);
                startActivity(join_intent);
    }
});

        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText에 입력되어있는 값을 get(가져온다)해온다
                String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();


                if(userID.equals("")||userPass.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( LogInActivity.this );
                    dialog=builder.setMessage("빈 칸을 채워주세요")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    api = RetrofitInit.getRetrofit();
                    Call<JsonObject> login = api.login(userID,userPass);
                    //enqueue -> 보내기
                    login.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("res", response.body().toString());
                            JsonObject serverRes = response.body();//서버에서 내려온 body는 jsonObject 형태
                            try {
                                //res = serverRes.get("result").getAsString();
                                res = serverRes.get("success").getAsString();
                            } catch (Exception e) {
                                e.printStackTrace();
                                res = "error";
                            }
                            if(res.equals("true")){
                                AlertDialog.Builder builder=new AlertDialog.Builder( LogInActivity.this );
                                dialog=builder.setMessage("로그인이 완료되었습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                Intent login_intent = new Intent(LogInActivity.this,RecordActivity.class);
                                login_intent.putExtra("id",userID);
                                login_intent.putExtra("pass",userPass);
                                login_intent.putExtra("name", serverRes.get("NAME").getAsString());
                                login_intent.putExtra("contact", serverRes.get("CONTACT").getAsString());
                                startActivity(login_intent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LogInActivity.this);
                                dialog = builder.setMessage("로그인에 실패하셨습니다.")
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
            };
        });
    }
}
