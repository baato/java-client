package com.baato.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.baato.baatolibrary.application.BaatoLib;
import com.baato.baatolibrary.models.DirectionsAPIResponse;

import com.baato.baatolibrary.models.ErrorResponse;
import com.baato.baatolibrary.navigation.NavigateResponseConverter;
import com.baato.baatolibrary.requests.BaatoAPI;
import com.baato.baatolibrary.utilities.ErrorUtils;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoRouting {
    private Context context;
    private BaatoRoutingRequestListener baatoRoutingRequestListener;
    private String accessToken, query, mode;
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
        directionsAPIResponseCall = baatoAPI.getDirections(accessToken, points, mode, alternatives, instructions);
        directionsAPIResponseCall
                .enqueue(new Callback<DirectionsAPIResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsAPIResponse> call, Response<DirectionsAPIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            baatoRoutingRequestListener.onSuccess(response.body());
                        } else {
                            ErrorResponse errorResponse = ErrorUtils.parseError(response, apiVersion, apiBaseUrl);
                            baatoRoutingRequestListener.onFailed(new Throwable(errorResponse.getMessage()));
//                                baatoRoutingRequestListener.onFailed(new Throwable(response.errorBody().string()));
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

    public static String getParsedNavResponse(DirectionsAPIResponse response, String mode,Locale locale, Context context) {
        return NavigateResponseConverter.convertFromGHResponse(response.getData().get(0), mode, locale, context).toString();
    }
    public static String getParsedNavResponse(DirectionsAPIResponse response, String mode, Context context) {
        return NavigateResponseConverter.convertFromGHResponse(response.getData().get(0), mode, context).toString();
    }
}
