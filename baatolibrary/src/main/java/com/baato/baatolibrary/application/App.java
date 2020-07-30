package com.baato.baatolibrary.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {
    private static TinyDB tinyDB;


    @Override
    public void onCreate() {
        super.onCreate();
        //tiny db config
        tinyDB = new TinyDB(getApplicationContext());
    }

    public static TinyDB db() {
        return tinyDB;
    }


    public static Retrofit retrofitV2(String apiVersion, String apiBaseURL) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(apiBaseURL+ "v" + apiVersion + "/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofitV2 = builder.build();
        return retrofitV2;
    }

    public static boolean isConnectedToNetwork(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }
        return isConnected;
    }
}
