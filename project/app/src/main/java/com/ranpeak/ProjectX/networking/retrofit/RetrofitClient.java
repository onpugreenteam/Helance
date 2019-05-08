package com.ranpeak.ProjectX.networking.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit;
    private static Gson gson;
    private static final String BASE_URL = "https://projectx174.herokuapp.com/";
//    private static final String BASE_URL = "http://192.168.0.113:8080/";

    public static synchronized Retrofit getInstance() {
        if (retrofit == null) {
            if (gson == null) {
                gson = new GsonBuilder().setLenient().create();
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

}