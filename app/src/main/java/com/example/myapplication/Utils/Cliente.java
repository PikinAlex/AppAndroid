package com.example.myapplication.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class Cliente {

    public static Retrofit getCliente(String url){
        OkHttpClient.Builder okHttpClientBuilder=new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(logging);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();


        Retrofit.Builder builder= new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClientBuilder.build());
        Retrofit retrofit=builder.build();

        return  retrofit;
    }

}
