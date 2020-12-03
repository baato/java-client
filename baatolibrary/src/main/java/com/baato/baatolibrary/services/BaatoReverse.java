package com.baato.baatolibrary.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baato.baatolibrary.application.BaatoLib;
import com.baato.baatolibrary.models.ErrorResponse;
import com.baato.baatolibrary.models.PlaceAPIResponse;
import com.baato.baatolibrary.models.LatLon;

import com.baato.baatolibrary.requests.BaatoAPI;
import com.baato.baatolibrary.utilities.ErrorUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoReverse {

    private static final String TAG = "BaatoReverseGeoCode";
    private Context context;
    private BaatoReverseRequestListener baatoReverseRequestListener;
    private String accessToken;
    private String apiVersion = "1";
    private String apiBaseUrl = "https://api.baato.io/api/";
    private int radius, limit = 0;
    private LatLon latLon;
    private Call<PlaceAPIResponse> placeAPIResponseCall;

    public interface BaatoReverseRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't search places
         */
        void onSuccess(PlaceAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoReverse(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoReverse setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the apiVersion. By default it takes version "1"
     */
    public BaatoReverse setAPIVersion(@NonNull String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * Set the apiBaseURL.
     */
    public BaatoReverse setAPIBaseURL(@NonNull String apiBaseURL) {
        this.apiBaseUrl = apiBaseURL;
        return this;
    }


    /**
     * Set the geocode to search.
     */
    public BaatoReverse setLatLon(@NonNull LatLon latLon) {
        this.latLon = latLon;
        return this;
    }

    /**
     * Set the radius to search.
     */
    public BaatoReverse setRadius(@NonNull int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Set the limit to search.
     */
    public BaatoReverse setLimit(@NonNull int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoReverseRequestListener the listener to be notified
     * @return this
     */
    public BaatoReverse withListener(BaatoReverseRequestListener baatoReverseRequestListener) {
        this.baatoReverseRequestListener = baatoReverseRequestListener;
        return this;
    }

    public void doRequest() {
        BaatoAPI baatoAPI = BaatoLib.retrofitV2(apiVersion, apiBaseUrl).create(BaatoAPI.class);
        placeAPIResponseCall=baatoAPI.performReverseGeoCode(giveMeQueryFilter());
        placeAPIResponseCall.enqueue(new Callback<PlaceAPIResponse>() {
            @Override
            public void onResponse(Call<PlaceAPIResponse> call, Response<PlaceAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoReverseRequestListener.onSuccess(response.body());
                else {
                    try {
                        ErrorResponse errorResponse = ErrorUtils.parseError(response, apiVersion, apiBaseUrl);
                        baatoReverseRequestListener.onFailed(new Throwable(errorResponse.getMessage()));
//                        baatoReverseRequestListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceAPIResponse> call, Throwable throwable) {
                baatoReverseRequestListener.onFailed(throwable);
            }
        });
    }
    public void cancelRequest() {
        placeAPIResponseCall.cancel();
    }

    private Map<String, String> giveMeQueryFilter() {
        Map<String, String> queryMap = new HashMap<>();
        //compulsory
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (limit != 0)
            queryMap.put("limit", limit + "");
        if (latLon.lat != 0.00)
            queryMap.put("lat", latLon.lat + "");
        if (latLon.lon != 0.00)
            queryMap.put("lon", latLon.lon + "");

        //optional
        if (radius != 0)
            queryMap.put("radius", radius + "");

        return queryMap;
    }
}
