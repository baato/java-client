package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.Geocode;

import com.kathmandulivinglabs.baatolibrary.models.PlaceAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoReverseGeoCode {

    private static final String TAG = "BaatoReverseGeoCode";
    private Context context;
    private BaatoReverseGeoCodeRequestListener baatoSearchRequestListener;
    private String accessToken;
    private int radius;
    private Geocode geocode;

    public interface BaatoReverseGeoCodeRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't search places
         */
        void onSuccess(PlaceAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoReverseGeoCode(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoReverseGeoCode setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the geocode to search.
     */
    public BaatoReverseGeoCode setGeoCode(@NonNull Geocode geoCode) {
        this.geocode = geoCode;
        return this;
    }

    /**
     * Set the radius to search.
     */
    public BaatoReverseGeoCode setRadius(@NonNull int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoSearchRequestListener the listener to be notified
     * @return this
     */
    public BaatoReverseGeoCode withListener(BaatoReverseGeoCodeRequestListener baatoSearchRequestListener) {
        this.baatoSearchRequestListener = baatoSearchRequestListener;
        return this;
    }

    public void doReverseGeoCode() {
        QueryAPI queryAPI = App.retrofitV2().create(QueryAPI.class);
        queryAPI.performReverseGeoCode(giveMeQueryFilter()).enqueue(new Callback<PlaceAPIResponse>() {
            @Override
            public void onResponse(Call<PlaceAPIResponse> call, Response<PlaceAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoSearchRequestListener.onSuccess(response.body());
                else {
                    try {
                        baatoSearchRequestListener.onFailed(new Throwable(response.errorBody().string()));
                        Log.d(TAG, "onResponse: ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceAPIResponse> call, Throwable throwable) {
                baatoSearchRequestListener.onFailed(throwable);
            }
        });
    }

    private Map<String, String> giveMeQueryFilter() {
        Map<String, String> queryMap = new HashMap<>();
        //compulsory
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (geocode.lat != 0.00)
            queryMap.put("lat", geocode.lat + "");
        if (geocode.lon != 0.00)
            queryMap.put("lon", geocode.lon + "");

        //optional
        if (radius != 0)
            queryMap.put("radius", radius + "");

        return queryMap;
    }
}
