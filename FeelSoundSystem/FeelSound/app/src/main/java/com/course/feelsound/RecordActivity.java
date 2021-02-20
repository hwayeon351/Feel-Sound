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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordActivity extends AppCompatActivity {
    RetrofitAPI api;
    String res;
    private RecyclerView myRecyclerView;
    private RecyclerView.LayoutManager myLayoutManager;
    Button set_snd_btn, snd_det_btn, customer_btn;
    //Button set_not_btn, set_not_btn1, set_not_btn2, set_not_btn3, set_not_btn4;
    String id,pass,name,contact;
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> Adapter;
    StringBuffer sb = new StringBuffer();

    //notification
    NotificationManager manager;
    NotificationCompat.Builder builder;
    private static String CHANNEL_ID = "channel1";
    private static String CHANEL_NAME = "Channel1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "feelsound.db", null, 1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);
        setTitle("소리 기록");
        ArrayList<Record> baby_records = new ArrayList<>();
        ArrayList<Record> car_records = new ArrayList<>();
        ArrayList<Record> dog_records = new ArrayList<>();
        ArrayList<Record> jack_records = new ArrayList<>();
        ArrayList<Record> siren_records = new ArrayList<>();
        ArrayList<Record> total_records = new ArrayList<>();


        //사용자 정보
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        pass = intent.getExtras().getString("pass");
        name = intent.getExtras().getString("name");
        contact = intent.getExtras().getString("contact");

        //db에서 record 가져오기
        api = RetrofitInit.getRetrofit();
        Call<JsonArray> record = api.record(id);
        //enqueue -> 보내기
        record.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                JsonArray serverRes = response.body();
                Log.d("blabla", response.body().toString());
                try {
                    for(int i=0; i < serverRes.size(); i++){
                        JsonObject jObject = serverRes.get(i).getAsJsonObject();
                        String SUCCESS= jObject.get("success").getAsString();
                        int S_NUM= Integer.parseInt(jObject.get("S_NUM").getAsString());
                        String S_NAME = jObject.get("S_NAME").getAsString();
                        String TIME = jObject.get("TIME").getAsString();
                        //list.add(S_NUM+" "+S_NAME+ " " + TIME);
                        total_records.add(new Record(S_NUM,S_NAME,TIME));
                        if(S_NUM == 0){
                            baby_records.add(new Record(S_NUM,S_NAME,TIME));
                        }
                        else if(S_NUM == 1){
                            car_records.add(new Record(S_NUM,S_NAME,TIME));
                        }
                        else if(S_NUM == 2){
                            dog_records.add(new Record(S_NUM,S_NAME,TIME));
                        }
                        else if(S_NUM == 3){
                            jack_records.add(new Record(S_NUM,S_NAME,TIME));
                        }
                        else if(S_NUM == 4){
                            siren_records.add(new Record(S_NUM,S_NAME,TIME));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    res = "error";
                }
            }
            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {//php까지 도달이 안 된 상태
                t.printStackTrace();
            }
        });


        //button
        set_snd_btn = findViewById(R.id.record_btn);
        snd_det_btn = findViewById(R.id.set_btn);
        customer_btn = findViewById(R.id.customer_btn);
        /*
        set_not_btn = findViewById(R.id.not_btn);
        set_not_btn1 = findViewById(R.id.not_btn1);
        set_not_btn2 = findViewById(R.id.not_btn2);
        set_not_btn3 = findViewById(R.id.not_btn3);
        set_not_btn4 = findViewById(R.id.not_btn4);
*/
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.set_btn:
                        Intent danger_intent = new Intent(RecordActivity.this, SetDangerActivity.class);
                        danger_intent.putExtra("id", id);
                        danger_intent.putExtra("pass", pass);
                        danger_intent.putExtra("name", name);
                        danger_intent.putExtra("contact", contact);
                        startActivity(danger_intent);
                        break;
                    case R.id.customer_btn:
                        Intent customer_intent = new Intent(RecordActivity.this, CustomerServiceActivity.class);
                        customer_intent.putExtra("id", id);
                        customer_intent.putExtra("pass", pass);
                        customer_intent.putExtra("name", name);
                        customer_intent.putExtra("contact", contact);
                        startActivity(customer_intent);
                        break;
                        /*
                    case R.id.not_btn:
                        showNoti("아기 울음 소리가 감지","baby");
                        break;
                    case R.id.not_btn1:
                        showNoti("차 경적 소리가 감지","car");
                        break;
                    case R.id.not_btn2:
                        showNoti("개 짖는 소리가 감지","dog");
                        break;
                    case R.id.not_btn3:
                        showNoti("사이렌 소리가 감지","siren");
                        break;
                    case R.id.not_btn4:
                        showNoti("굴착기 소리가 감지","jackhammer");
                        break;*/
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            }
        };
        snd_det_btn.setOnClickListener(onClickListener);
        customer_btn.setOnClickListener(onClickListener);
        /*
        set_not_btn.setOnClickListener(onClickListener);
        set_not_btn1.setOnClickListener(onClickListener);
        set_not_btn2.setOnClickListener(onClickListener);
        set_not_btn3.setOnClickListener(onClickListener);
        set_not_btn4.setOnClickListener(onClickListener);
*/


        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myRecyclerView.setHasFixedSize(true);
        myLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(myLayoutManager);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.select_group);

        MyAdapter myAdapter = new MyAdapter(total_records);
        myRecyclerView.setAdapter(myAdapter);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id == R.id.total_btn) {
                    //ArrayList<Record> total_result = new ArrayList<>();
                    //total_result = dbHelper.getTotalRecord();
                    MyAdapter myAdapter = new MyAdapter(total_records);
                    myRecyclerView.setAdapter(myAdapter);
                } else if (id == R.id.babyCrying_btn) {
                    MyAdapter myAdapter = new MyAdapter(baby_records);
                    myRecyclerView.setAdapter(myAdapter);
                } else if (id == R.id.car_btn) {
                    MyAdapter myAdapter = new MyAdapter(car_records);
                    myRecyclerView.setAdapter(myAdapter);
                } else if (id == R.id.dog_btn) {
                    MyAdapter myAdapter = new MyAdapter(dog_records);
                    myRecyclerView.setAdapter(myAdapter);
                } else if (id == R.id.siren_btn) {
                    MyAdapter myAdapter = new MyAdapter(siren_records);
                    myRecyclerView.setAdapter(myAdapter);
                } else if (id == R.id.jackhammer_btn) {
                    MyAdapter myAdapter = new MyAdapter(jack_records);
                    myRecyclerView.setAdapter(myAdapter);
                }

            }
        });
    }
    public void showNoti(String not, String img){
        builder = null;
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE); //버전 오레오 이상일 경우
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            manager.createNotificationChannel( new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT) );
            builder = new NotificationCompat.Builder(this,CHANNEL_ID); //하위 버전일 경우
        }
        else{
            builder = new NotificationCompat.Builder(this);
        }

        builder.setVibrate(new long[]{10000,2000,1000,3000}); //5초대기, 2초진동, 1초대기, 3초진동



        //알림창 제목
        builder.setContentTitle("주의");
        //알림창 메시지
        builder.setContentText(not);
        //알림창 이미지
        Bitmap bm;
        switch (img) {
            case "baby":
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.baby_crying);
                break;
            case "car":
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.car_horn);
                break;
            case "dog":
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.barking_dog);
                break;
            case "siren":
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.siren);
                break;
            case "jackhammer":
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.jackhammer);
                break;
            default:
                bm= BitmapFactory.decodeResource(getResources(),R.drawable.barking_dog);
                break;
        }
        builder.setLargeIcon(bm);
        //알림창 아이콘
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        Notification notification = builder.build();
        //알림창 실행
        manager.notify(1,notification);
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
                Intent my_intent = new Intent(RecordActivity.this, MyPageActivity.class);
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
