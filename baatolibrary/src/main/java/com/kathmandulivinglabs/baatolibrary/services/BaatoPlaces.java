package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.AutoCompleteAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoPlaces {

    private Context context;
    private String accessToken, query;
    private BaatoPlacesListener baatoPlacesListener;
    private int radius = 0, limit = 0;
    private double lat = 0.00, lon = 0.00;

    public interface BaatoPlacesListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(AutoCompleteAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoPlaces(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoPlaces setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoPlaces setQuery(@NonNull String query) {
        this.query = query;
        return this;
    }

    /**
     * Set the radius to search.
     */
    public BaatoPlaces setRadius(@NonNull int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Set the limit to search.
     */
    public BaatoPlaces setLimit(@NonNull int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Set the lat and long value.
     */
    public BaatoPlaces setLatLong(@NonNull double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoAutoCompleteListener the listener to be notified
     * @return this
     */
    public BaatoPlaces withListener(BaatoPlacesListener baatoAutoCompleteListener) {
        this.baatoPlacesListener = baatoAutoCompleteListener;
        return this;
    }

    public void doAutoComplete() {
        QueryAPI queryAPI = App.retrofitV2().create(QueryAPI.class);
        queryAPI.performAutoComplete(giveMeQueryFilter()).enqueue(new Callback<AutoCompleteAPIResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteAPIResponse> call, Response<AutoCompleteAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoPlacesListener.onSuccess(response.body());
                else {
                    try {
                        baatoPlacesListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AutoCompleteAPIResponse> call, Throwable throwable) {
                baatoPlacesListener.onFailed(throwable);
            }
        });
    }

    private Map<String, String> giveMeQueryFilter() {
        Map<String, String> queryMap = new HashMap<>();
        //compulsory ones
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (query != null)
            queryMap.put("q", query);

        //optionals
        if (radius != 0)
            queryMap.put("radius", radius + "");
        if (limit != 0)
            queryMap.put("limit", limit + "");
        if (lat != 0.00)
            queryMap.put("lat", lat + "");
        if (lon != 0.00)
            queryMap.put("lon", lon + "");
        return queryMap;
    }
}
