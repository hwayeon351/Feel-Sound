package com.course.feelsound;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //DB 관리 클래스
    //테이블 생성, 데이터 삽입, 삭제
    //생성자, onCreate, onUpgrade 필수
    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        String create_sound_sql = "CREATE TABLE IF NOT EXISTS SOUND (SOUND_ID INT PRIMARY KEY, NAME TEXT NOT NULL, TYPE INT NOT NULL, VIB_P INT NOT NULL, VIB_S INT NOT NULL, STATE INT NOT NULL);";
        String create_record_sql = "CREATE TABLE IF NOT EXISTS RECORD (RECORD_ID INTEGER PRIMARY KEY AUTOINCREMENT, S_TYPE INT NOT NULL, S_NAME TEXT NOT NULL,TIME TEXT NOT NULL);";

        db.execSQL(create_sound_sql);
        db.execSQL(create_record_sql);
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SOUND");
        db.execSQL("DROP TABLE IF EXISTS RECORD");
        onCreate(db);
    }

    public void insert_sound(int sound_id, String name, int type, int vib_p, int vib_s, int state) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // SOUND TABLE에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SOUND VALUES('" + sound_id + "' , '" + name + "', '" + type + "', '" + vib_p + "', '" + vib_s + "' , '" + state + "');");
        Log.i("insert sound","ddd");
        db.close();
    }

    public int check_sound(int s_id){
        SQLiteDatabase db = getReadableDatabase();

        int cnt = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM SOUND WHERE SOUND_ID = " + s_id +"",null);
        cnt = cursor.getCount();

        return cnt;
    }

    public void insert_record(int s_type, String s_name, String time) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // RECORD TABLE에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO RECORD(S_TYPE, S_NAME, TIME) VALUES( '" + s_type + "', '" + s_name + "' , '" + time + "');");
        Log.i("insert record","aaaaaa");
        db.close();
    }

    public void update_sound(int sound_id, int vib_p, int vib_s, int state){
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 아이디가 일치하는 행의 진동 패턴, 진동 크기 정보 수정
        db.execSQL("UPDATE SOUND SET VIB_P = " + vib_p + " , VIB_S = " + vib_s + ", STATE = '" + state + "' WHERE SOUND_ID = '"+ sound_id +"';");
        db.close();
    }

    public void update_vib(int sound_id, int vib_p, int vib_s){
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 아이디가 일치하는 행의 진동 패턴, 진동 크기 정보 수정
        db.execSQL("UPDATE SOUND SET VIB_P = " + vib_p + " , VIB_S = " + vib_s + " WHERE SOUND_ID = '"+ sound_id +"';");
        db.close();
    }

    public void delete_sound(int sound_id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 시간과 날짜가 일치하는 행의 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM SOUND WHERE SOUND_ID = '" + sound_id + "';");
        db.close();
    }

    public void delete_record(String time) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 시간과 날짜가 일치하는 행의 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM RECORD WHERE TIME = '" + time + "';");
        db.close();
    }

    public String select_total_record() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT S_TYPE, S_NAME, TIME FROM RECORD", null);
        while (cursor.moveToNext()) {
            result += cursor.getInt(0)
                    + " | "
                    + cursor.getString(1)
                    + " | "
                    + cursor.getString(2)
                    + "\n";
        }
        return result;
    }



    public String select_danger_record() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT S_NAME, TIME FROM RECORD WHERE S_TYPE = 1",null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) //소리 종류
                    + " | "
                    + cursor.getString(1) //시간
                    + "\n";
        }
        return result;
    }

    public String select_daily_record() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT S_NAME, TIME FROM RECORD WHERE S_TYPE = 0",null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) //소리 종류
                    + " | "
                    + cursor.getString(1) //시간
                    + "\n";
        }
        return result;
    }

    public int soundCount(){
        SQLiteDatabase db = getReadableDatabase();
        int cnt = 0;
        Cursor cursor = db.rawQuery("SELECT * FROM Sound", null);
        cnt = cursor.getCount();

        return cnt;
    }

    public ArrayList<Sound> getSoundList(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SOUND_ID, TYPE, STATE, VIB_P, VIB_S FROM SOUND",null);

        ArrayList<Sound> sound_list = new ArrayList<>();
        while(cursor.moveToNext()){
            int s_id = cursor.getInt(0);
            int type = cursor.getInt(1);
            int state = cursor.getInt(2);
            int vib_p = cursor.getInt(3);
            int vib_s = cursor.getInt(4);

            sound_list.add(new Sound(s_id,type,vib_p,vib_s,state));
        }

        return sound_list;
    }

    public ArrayList<Record> getDangerRecord(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT S_NAME, TIME FROM RECORD WHERE S_TYPE = 1",null);

        ArrayList<Record> danger_record = new ArrayList<>();
        while(cursor.moveToNext()){
            String s_name = cursor.getString(0);
            String time = cursor.getString(1);

            danger_record.add(new Record(s_name,time));
        }

        return danger_record;
    }

    public ArrayList<Record> getDailyRecord(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT S_NAME, TIME FROM RECORD WHERE S_TYPE = 0",null);

        ArrayList<Record> daily_record = new ArrayList<>();
        while(cursor.moveToNext()){
            String s_name = cursor.getString(0);
            String time = cursor.getString(1);

            daily_record.add(new Record(s_name,time));
        }

        return daily_record;
    }

    public ArrayList<Record> getTotalRecord(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT S_NAME, TIME FROM RECORD",null);

        ArrayList<Record> total_record = new ArrayList<>();
        while(cursor.moveToNext()){
            String s_name = cursor.getString(0);
            String time = cursor.getString(1);

            total_record.add(new Record(s_name,time));
        }
        return total_record;
    }

}

