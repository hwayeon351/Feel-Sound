package com.course.feelsound;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @GET("Join.php")
       Call<JsonObject> join(
            @Query("_id") String userID,
            @Query("pass") String userPass,
            @Query("name") String userName,
            @Query("phone") String userPhone);//php 응답상태를 파악하기 위함

    @GET("Login.php")
    Call<JsonObject> login(
            @Query("_id") String userID,
            @Query("pass") String userPass);//php 응답상태를 파악하기 위함

    @GET("Customer_Service.php")
    Call<JsonObject> customer(
            @Query("_id") String userID,
            @Query("CONTENT") String content,
            @Query("TIME") String time);

    @GET("Customer_Service_s.php")
    Call<JsonObject> customer_s(
            @Query("_id") String userID,
            @Query("S_NAME") String s_name,
            @Query("TIME") String time);

    @GET("Sound_Setting.php")
    Call<JsonObject> s_setting(
            @Query("_id") String userID,
            @Query("S_NAME") String s_name,
            @Query("S_NUM") String s_num,
            @Query("VIB_P") String vib_p,
            @Query("VIB_S") String vib_s);

    @GET("Record.php")
    Call<JsonArray> record(
            @Query("_id") String userID);


}
