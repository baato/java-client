package com.baato.baatolibrary.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class Retry implements Interceptor {
    public int maxRetry; // maximum number of retries
    private int retryNum = 0; // If set to 3 retry, the maximum possible request 4 times (default 1 + 3 retry)
    public Retry(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            response.close();
//            Log.i("Retry","num:"+retryNum);
            response = chain.proceed(request);
        }
        return response;
    }

}
