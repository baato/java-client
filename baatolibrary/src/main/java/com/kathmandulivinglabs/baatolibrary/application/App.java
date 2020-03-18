package com.kathmandulivinglabs.baatolibrary.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

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

    public static Retrofit retrofit(final String token) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://178.128.59.143/api/v1/")
                .addConverterFactory(GsonConverterFactory.create());

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });
        builder.client(okHttpBuilder.build());
        Retrofit retrofit = builder.build();
        return retrofit;
    }

    public static Retrofit retrofitV2() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://178.128.59.143:8080/api/v2/")
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
