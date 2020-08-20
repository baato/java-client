package com.baato.baatolibrary.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baato.baatolibrary.utilities.Retry;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaatoLib extends Application {
    private static TinyDB tinyDB;
//    @SuppressLint("StaticFieldLeak")
//    private static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();
        //tiny db config
        tinyDB = new TinyDB(getApplicationContext());
//        mContext = this;
    }

    public static TinyDB db() {
        return tinyDB;
    }


    public static Retrofit retrofitV2(String apiVersion, String apiBaseURL) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new Retry(4))
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
//    public static Context getContext(){
//        return mContext;
//    }
}
