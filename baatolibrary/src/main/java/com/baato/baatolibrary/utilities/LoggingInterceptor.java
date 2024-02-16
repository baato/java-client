package com.baato.baatolibrary.utilities;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        // Log request headers
        System.out.println("Request Headers: " + originalRequest.headers().get("SessionId"));
        System.out.println("Request Headers: " + originalRequest.headers().get("BundleIdentifier"));

        // Log request body if present
        if (originalRequest.body() != null) {
            Buffer buffer = new Buffer();
            originalRequest.body().writeTo(buffer);
            System.out.println("Request Body: " + buffer.readUtf8());
        }

        Response response = chain.proceed(originalRequest);

        // Log response body if present
        ResponseBody responseBody = response.body();
        if (responseBody != null) {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            System.out.println("Response Body: " + buffer.clone().readUtf8());
        }

        return response;
    }
}
