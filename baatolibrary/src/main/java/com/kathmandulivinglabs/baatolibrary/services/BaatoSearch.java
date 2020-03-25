package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.Place;
import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoSearch {
    private Context context;
    private BaatoSearchRequestListener baatoSearchRequestListener;
    private String accessToken, query;
    private String type;
    private int radius = 0, limit = 0, placeId = 0;
    private double lat = 0.00, lon = 0.00;

    public interface BaatoSearchRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(SearchAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoSearch(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoSearch setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoSearch setQuery(@NonNull String query) {
        this.query = query;
        return this;
    }

    /**
     * Set the placeId.
     */
    public BaatoSearch setPlaceId(@NonNull int placeId) {
        this.placeId = placeId;
        return this;
    }

    /**
     * Set the radius to search.
     */
    public BaatoSearch setRadius(@NonNull int radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Set the type.
     */
    public BaatoSearch setType(@NonNull String type) {
        this.type = type;
        return this;
    }

    /**
     * Set the limit to search.
     */
    public BaatoSearch setLimit(@NonNull int limit) {
        this.limit = limit;
        return this;
    }

    /**
     * Set the lat and long value.
     */
    public BaatoSearch setLatLong(@NonNull double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoSearchRequestListener the listener to be notified
     * @return this
     */
    public BaatoSearch withListener(BaatoSearchRequestListener baatoSearchRequestListener) {
        this.baatoSearchRequestListener = baatoSearchRequestListener;
        return this;
    }

    public void doSearch() {
        QueryAPI queryAPI = App.retrofitV2().create(QueryAPI.class);
        queryAPI.searchQuery(giveMeQueryFilter()).enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoSearchRequestListener.onSuccess(response.body());
                else {
                    try {
                        baatoSearchRequestListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchAPIResponse> call, Throwable throwable) {
                baatoSearchRequestListener.onFailed(throwable);
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
        if (placeId != 0)
            queryMap.put("placeId", placeId + "");

        //optionals
        if (type != null)
            queryMap.put("type", type);
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
