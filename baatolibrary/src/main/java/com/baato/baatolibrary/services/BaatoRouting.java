package com.baato.baatolibrary.services;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.baato.baatolibrary.application.BaatoLib;
import com.baato.baatolibrary.models.DirectionsAPIResponse;

import com.baato.baatolibrary.models.ErrorResponse;
import com.baato.baatolibrary.navigation.NavigateResponseConverter;
import com.baato.baatolibrary.requests.BaatoAPI;
import com.baato.baatolibrary.utilities.BaatoUtil;
import com.baato.baatolibrary.utilities.ErrorUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

@Keep
public class BaatoRouting {
    private Context context;
    private BaatoRoutingRequestListener baatoRoutingRequestListener;
    private String accessToken, securityCode, mode;
    private String apiVersion = "1";
    private String apiBaseUrl = "https://api.baato.io/api/";
    private String[] points;
    private Boolean alternatives;
    private Boolean instructions;
    private Call<DirectionsAPIResponse> directionsAPIResponseCall;
    private Locale locale;


    public interface BaatoRoutingRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(DirectionsAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoRouting(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoRouting setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the apiVersion. By default it takes version "1"
     */
    public BaatoRouting setAPIVersion(@NonNull String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public BaatoRouting setLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public BaatoRouting setContext(Context context) {
        this.context = context;
        return this;
    }

    /**
     * Set the apiBaseURL.
     */
    public BaatoRouting setAPIBaseURL(@NonNull String apiBaseURL) {
        this.apiBaseUrl = apiBaseURL;
        return this;
    }


    /**
     * Set the mode.
     */
    public BaatoRouting setMode(@NonNull String mode) {
        this.mode = mode;
        return this;
    }


    /**
     * Set the query to search.
     */
    public BaatoRouting setAlternatives(@NonNull Boolean alternatives) {
        this.alternatives = alternatives;
        return this;
    }

    /**
     * Set true to get instruction list
     */
    public BaatoRouting setInstructions(Boolean instructions) {
        this.instructions = instructions;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoRouting setPoints(@NonNull String[] points) {
        this.points = points;
        return this;
    }

    /**
     * Set the securityCode is security enabled.
     */
    public BaatoRouting setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoRoutingRequestListener the listener to be notified
     * @return this
     */
    public BaatoRouting withListener(BaatoRoutingRequestListener baatoRoutingRequestListener) {
        this.baatoRoutingRequestListener = baatoRoutingRequestListener;
        return this;
    }

    public void doRequest() {
        BaatoAPI baatoAPI = BaatoLib.retrofitV2(apiVersion, apiBaseUrl).create(BaatoAPI.class);
        directionsAPIResponseCall = baatoAPI.getDirections(giveMeQueryFilter(context), points);
        directionsAPIResponseCall
                .enqueue(new Callback<DirectionsAPIResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsAPIResponse> call, Response<DirectionsAPIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            baatoRoutingRequestListener.onSuccess(response.body());
                        } else {
                            ErrorResponse errorResponse = ErrorUtils.parseError(response, apiVersion, apiBaseUrl);
                            baatoRoutingRequestListener.onFailed(new Throwable(errorResponse.getMessage()));
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsAPIResponse> call, Throwable t) {
                        baatoRoutingRequestListener.onFailed(t);
                    }
                });

    }

    public void cancelRequest() {
        directionsAPIResponseCall.cancel();
    }

    public static String getParsedNavResponse(DirectionsAPIResponse response, String mode, Locale locale, Context context) {
        return NavigateResponseConverter.convertFromGHResponse(response.getData().get(0), mode, locale, context).toString();
    }

    public static String getParsedNavResponse(DirectionsAPIResponse response, String mode, Context context) {
        return NavigateResponseConverter.convertFromGHResponse(response.getData().get(0), mode, context).toString();
    }

    private Map<String, Object> giveMeQueryFilter(Context context) {
        Map<String, Object> queryMap = new HashMap<>();
        //compulsory ones
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (mode != null)
            queryMap.put("mode", mode);
        if (alternatives != null)
            queryMap.put("instructions", instructions);
        if (alternatives != null)
            queryMap.put("alternatives", alternatives);

        //optionals
        if (securityCode != null && !securityCode.isEmpty())
            queryMap.put("hash", BaatoUtil.generateHash(context.getPackageName(), accessToken, securityCode));
        return queryMap;
    }
}
