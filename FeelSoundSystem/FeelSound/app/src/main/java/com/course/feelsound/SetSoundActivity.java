package com.course.feelsound;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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

public class SetSoundActivity extends AppCompatActivity {
    CheckBox baby_crying_btn,barking_dog_btn, car_horn_btn, jackham_btn, siren_btn;
    RadioButton v_low, v_strong, v_one, v_two;
    private Messenger mServiceMessenger = null;
    Button set_snd_btn, snd_det_btn, customer_btn, save_btn;
    //TextView test1;
    String id,pass,name,contact;
    RetrofitAPI api;
    String res="";
    private AlertDialog dialog;

    int baby= 0;
    int dog = 1;
    int car = 2;
    int jack = 3;
    int siren = 4;

    private long ckbox_press_time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_sound_layout);

        setTitle("소리 설정");
        //사용자 정보
        Intent intent = getIntent();
        id = intent.getExtras().getString("id");
        pass = intent.getExtras().getString("pass");
        name = intent.getExtras().getString("name");
        contact = intent.getExtras().getString("contact");

        //test1 = (TextView)findViewById(R.id.test);


        //button
        set_snd_btn = findViewById(R.id.record_btn);
        customer_btn = findViewById(R.id.customer_btn);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.record_btn:/*
                        Intent record_intent = new Intent(SetSoundActivity.this, RecordActivity.class);
                        record_intent.putExtra("id", id);
                        record_intent.putExtra("pass", pass);
                        record_intent.putExtra("name", name);
                        record_intent.putExtra("contact", contact);
                        startActivity(record_intent);*/
                        finish();
                        break;
                    case R.id.customer_btn:
                        finish();
                        Intent customer_intent = new Intent(SetSoundActivity.this, CustomerServiceActivity.class);
                        customer_intent.putExtra("id", id);
                        customer_intent.putExtra("pass", pass);
                        customer_intent.putExtra("name", name);
                        customer_intent.putExtra("contact", contact);
                        startActivity(customer_intent);
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getId());
                }
            }
        };
        set_snd_btn.setOnClickListener(onClickListener);
        customer_btn.setOnClickListener(onClickListener);


        baby_crying_btn = (CheckBox) findViewById(R.id.babyCrying);
        barking_dog_btn = (CheckBox) findViewById(R.id.dog_bark);
        car_horn_btn = (CheckBox) findViewById(R.id.car_horn);
        jackham_btn = (CheckBox) findViewById(R.id.jackhammer);
        siren_btn = (CheckBox) findViewById(R.id.siren);

        v_low = (RadioButton) findViewById(R.id.v_low);
        v_strong = (RadioButton) findViewById(R.id.v_strong);
        v_one = (RadioButton) findViewById(R.id.v_one);
        v_two = (RadioButton) findViewById(R.id.v_two);

        /*
        try{
            bindService(new Intent(this, BluetoothDataService.class),mConnection, Context.BIND_AUTO_CREATE );
        }catch (Exception e){
            Log.e("TEST", e.toString());
        }*/

        baby_crying_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > ckbox_press_time + 1000) {
                    ckbox_press_time = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() <= ckbox_press_time + 1000) {
                    Intent it = new Intent(SetSoundActivity.this, SetVibrationActivity.class);
                    it.putExtra("s_num", "0");
                    it.putExtra("id",id);
                    startActivity(it);
                }
            }
        });
        barking_dog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > ckbox_press_time + 1000) {
                    ckbox_press_time = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() <= ckbox_press_time + 1000) {
                    Intent it = new Intent(SetSoundActivity.this, SetVibrationActivity.class);
                    it.putExtra("s_num", "2");
                    it.putExtra("id",id);
                    startActivity(it);
                }
            }
        });
        car_horn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > ckbox_press_time + 1000) {
                    ckbox_press_time = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() <= ckbox_press_time + 1000) {
                    Intent it = new Intent(SetSoundActivity.this, SetVibrationActivity.class);
                    it.putExtra("s_num", "1");
                    it.putExtra("id",id);
                    startActivity(it);
                }
            }
        });
        jackham_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > ckbox_press_time + 1000) {
                    ckbox_press_time = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() <= ckbox_press_time + 1000) {
                    Intent it = new Intent(SetSoundActivity.this, SetVibrationActivity.class);
                    it.putExtra("s_num", "4");
                    it.putExtra("id",id);
                    startActivity(it);
                }
            }
        });
        siren_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (System.currentTimeMillis() > ckbox_press_time + 1000) {
                    ckbox_press_time = System.currentTimeMillis();
                    return;
                }
                if (System.currentTimeMillis() <= ckbox_press_time + 1000) {
                    Intent it = new Intent(SetSoundActivity.this, SetVibrationActivity.class);
                    it.putExtra("s_num", "3");
                    it.putExtra("id",id);
                    startActivity(it);
                }
            }
        });

        save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(new View.OnClickListener() {
            int vib_p = 2;
            int vib_s = 2;
            @Override
            public void onClick(View v) {
                int[] check= new int[5];

                if(v_low.isChecked()){
                    vib_s = 1;
                }
                if(v_strong.isChecked()){
                    vib_s = 2;
                }
                if(v_one.isChecked()){
                    vib_p = 1;
                }
                if(v_two.isChecked()){
                    vib_p = 2;
                }

                if(baby_crying_btn.isChecked()){
                    check[0]=1;
                }
                else if(!baby_crying_btn.isChecked()){
                    check[0]=0;
                }
                if(car_horn_btn.isChecked()){
                    check[1]=1;
                }
                else if(!car_horn_btn.isChecked()){
                    check[1]=0;
                }
                if(barking_dog_btn.isChecked()){
                    check[2]=1;
                }
                else if(!barking_dog_btn.isChecked()){
                    check[2]=0;
                }
                if(siren_btn.isChecked()){
                    check[3]=1;
                }
                else if(!siren_btn.isChecked()){
                    check[3]=0;
                }
                if(jackham_btn.isChecked()){
                    check[4]=1;
                }
                else if(!jackham_btn.isChecked()){
                    check[4]=0;
                }
                int s_num = 0;
                while (s_num <= 4) {
                    String s_name;
                    if(check[s_num]==1) {
                        switch (s_num) {
                            case 0:
                                s_name = "아기 울음 소리";
                                break;
                            case 1:
                                s_name = "차 경적 소리";
                                break;
                            case 2:
                                s_name = "개 짖는 소리";
                                break;
                            case 3:
                                s_name = "사이렌 소리";
                                break;
                            case 4:
                                s_name = "굴착기 소리";
                                break;
                            default:
                                s_name = "";
                                break;
                        }
                    }
                    else {
                        s_name = "";
                    }
                    api = RetrofitInit.getRetrofit();
                    //test1.setText(String.valueOf(vib_p));
                    Call<JsonObject> s_setting = api.s_setting(id, s_name, String.valueOf(s_num), String.valueOf(vib_p), String.valueOf(vib_s));
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
                    s_num++;
                }
                if (res.equals("FAILED")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetSoundActivity.this);
                    dialog = builder.setMessage("다시 시도해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SetSoundActivity.this);
                    dialog = builder.setMessage("전송되었습니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                }
            }
        });
    }
/*
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("TEST", "DAILY SERVICE CONNECTED");
            mServiceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private Messenger mMessenger = new Messenger(new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.i("TEST", "SET DAILY MESSENGER");
            return false;
        }
    }));

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
                //saveBtnOnClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    public void saveBtnOnClicked() {
        final DBHelper dbHelper = new DBHelper(getApplicationContext(), "feelsound.db", null, 1);

        boolean ck_v_low = v_low.isChecked();
        boolean ck_v_strong = v_strong.isChecked();
        boolean ck_v_one = v_one.isChecked();
        boolean ck_v_two = v_two.isChecked();
        int vip_p = 0;
        int vip_s = 0;

        if(ck_v_low == true){
            vip_s = 1;
        }
        if(ck_v_strong == true){
            vip_s = 2;
        }
        if(ck_v_one == true){
            vip_p = 1;
        }
        if(ck_v_two == true){
            vip_p = 2;
        }


        if(barking_dog_btn.isChecked()){
            int cnt = dbHelper.check_sound(5);
            if(cnt == 0){
                dbHelper.insert_sound(5,"媛?吏뽯뒗 ?뚮━",1, vip_p, vip_s, 1);
            }
            else if(cnt > 0){
                dbHelper.update_sound(5, vip_p, vip_s, 1);
            }
        }
        else if(!barking_dog_btn.isChecked()){
            int cnt = dbHelper.check_sound(5);
            if(cnt == 0){
                dbHelper.insert_sound(5,"媛?吏뽯뒗 ?뚮━",1,0,0, 0);
            }
            else if(cnt > 0){
                dbHelper.update_sound(5, 0, 0, 0);
            }
        }

        if(car_horn_btn.isChecked()){
            int cnt = dbHelper.check_sound(4);
            if(cnt == 0){
                dbHelper.insert_sound(4,"李?寃쎌쟻 ?뚮━",1, vip_p, vip_s, 1);
            }
            else if(cnt > 0){
                dbHelper.update_sound(4, vip_p, vip_s, 1);
            }
        }
        else if(!car_horn_btn.isChecked()){
            int cnt = dbHelper.check_sound(4);
            if(cnt == 0){
                dbHelper.insert_sound(4,"李?寃쎌쟻 ?뚮━",1,0,0, 0);
            }
            else if(cnt > 0){
                dbHelper.update_sound(4, 0, 0, 0);
            }
        }

        if(jackham_btn.isChecked()){
            int cnt = dbHelper.check_sound(6);
            if(cnt == 0){
                dbHelper.insert_sound(6,"援댁갑湲??뚮━",1, vip_p, vip_s, 1);
            }
            else if(cnt > 0){
                dbHelper.update_sound(6, vip_p, vip_s, 1);
            }
        }
        else if(!jackham_btn.isChecked()){
            int cnt = dbHelper.check_sound(6);
            if(cnt == 0){
                dbHelper.insert_sound(6,"援댁갑湲??뚮━",1,0,0, 0);
            }
            else if(cnt > 0){
                dbHelper.update_sound(6, 0, 0, 0);
            }
        }

        if(siren_btn.isChecked()){
            int cnt = dbHelper.check_sound(7);
            if(cnt == 0){
                dbHelper.insert_sound(7,"?ъ씠???뚮━",1, vip_p, vip_s, 1);
            }
            else if(cnt > 0){
                dbHelper.update_sound(7, vip_p, vip_s, 1);
            }
        }
        else if(!siren_btn.isChecked()){
            int cnt = dbHelper.check_sound(7);
            if(cnt == 0){
                dbHelper.insert_sound(7,"?ъ씠???뚮━",1,0,0, 0);
            }
            else if(cnt > 0){
                dbHelper.update_sound(7, 0, 0, 0);
            }
        }
        String car = "";
        String dog = "";
        String jackham = "";
        String siren = "";

        String msg_setting = "";
        try{
            ArrayList<Sound> sound_list = dbHelper.getSoundList();
            for(int i = 0; i<sound_list.size(); i++){
                if(sound_list.get(i).type == 1){
                    if(sound_list.get(i).s_id == 4){
                        car += Integer.toString(sound_list.get(i).state) + " " + Integer.toString(sound_list.get(i).vib_p) + Integer.toString(sound_list.get(i).vib_s);
                    }
                    if(sound_list.get(i).s_id == 5){
                        dog += Integer.toString(sound_list.get(i).state) + " " + Integer.toString(sound_list.get(i).vib_p) + Integer.toString(sound_list.get(i).vib_s);
                    }
                    if(sound_list.get(i).s_id == 6){
                        jackham += Integer.toString(sound_list.get(i).state) + " " + Integer.toString(sound_list.get(i).vib_p) + Integer.toString(sound_list.get(i).vib_s);
                    }
                    if(sound_list.get(i).s_id == 7){
                        siren += Integer.toString(sound_list.get(i).state) + " " + Integer.toString(sound_list.get(i).vib_p) + Integer.toString(sound_list.get(i).vib_s);
                    }
                }
            }
            msg_setting = "Danger\n" + car + "\n" + dog + "\n" + jackham + "\n" + siren;

            sendMessageToService(msg_setting);
            Log.i("send",msg_setting+" ");
            Log.i("TEST", "bt clicked send message success");
        }catch (Exception e){
            Log.e("TEST", e.toString());
        }


        Toast.makeText(getApplicationContext(), "?ㅼ젙????λ릺?덉뒿?덈떎.", Toast.LENGTH_SHORT).show();
    }

    public void sendMessageToService(String str){
        if(mServiceMessenger != null){
            Log.i("TEST", "READY TO SEND MESSAGE TO SERIVCE");
            try{
                Message msg = Message.obtain(null, BluetoothDataService.MSG_SEND_TO_SERVICE,str);
                msg.replyTo = mMessenger;
                mServiceMessenger.send(msg);
                Log.i("TEST", "Activity sended message to service");
            }catch(Exception e){
                Log.i("TEST", e.toString());
            }
        }
    }
    */

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
                Intent my_intent = new Intent(SetSoundActivity.this, MyPageActivity.class);
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
}
