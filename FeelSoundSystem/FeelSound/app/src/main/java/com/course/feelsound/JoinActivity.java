package com.course.feelsound;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {
    TextView test1;
    RetrofitAPI api;
    String res;

    private EditText et_id, et_pass, et_name, et_phone, et_passck;
    private Button btn_register, btn_login, validateButton;
    private AlertDialog dialog;
    private boolean validate = false;
    boolean success= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_layout);
        setTitle("회원가입");

        //아이디 값 찾아주기
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);
        et_passck = findViewById(R.id.et_passck);

        btn_login=findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent join_intent = new Intent(JoinActivity.this,LogInActivity.class);
                startActivity(join_intent);
            }
        });
        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText에 입력되어있는 값을 get(가져온다)해온다
                String userID=et_id.getText().toString();
                String userPass=et_pass.getText().toString();
                String userName=et_name.getText().toString();
                String userPhone=et_phone.getText().toString();
                final String PassCk=et_passck.getText().toString();

                test1 = (TextView) findViewById(R.id.test);

                if(userID.equals("")||userPass.equals("")||userName.equals("")||userPhone.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity.this );
                    dialog=builder.setMessage("빈 칸을 채워주세요")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                else if(!userPass.equals(PassCk)) {
                    AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity.this );
                    dialog=builder.setMessage("비밀번호가 일치하지 않습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                else {
                    api = RetrofitInit.getRetrofit();
                    Call<JsonObject> join = api.join(userID,userPass,userName,userPhone);
                    //enqueue -> 보내기
                    join.enqueue(new Callback<JsonObject>() {
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
                            success = res.equals("SUCCESS");
                            if(res.equals("SUCCESS")){
                                AlertDialog.Builder builder=new AlertDialog.Builder( JoinActivity.this );
                                dialog=builder.setMessage("회원가입이 완료되었습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                finish(); //액티비티를 종료시킴(회원등록 창을 닫음)
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(JoinActivity.this);
                                dialog = builder.setMessage("사용할 수 없는 아이디입니다.")
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
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }
}