package com.course.feelsound;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInit {
    private static Retrofit retrofit;
    private static RetrofitAPI api;
    public static RetrofitAPI getRetrofit(){

        if(retrofit == null || api == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://cha6511.cafe24.com/Hwayeon/Seeun/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            api = retrofit.create(RetrofitAPI.class);
            return api;
        } return api;
    }
}
