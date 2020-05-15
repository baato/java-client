package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.PlaceAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoPlaces {

    private Context context;
    private String accessToken;
    private BaatoPlacesListener baatoPlacesListener;
    private int placeId = 0;

    public interface BaatoPlacesListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(PlaceAPIResponse places);

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
     * Set the placeId.
     */
    public BaatoPlaces setPlaceId(@NonNull int placeId) {
        this.placeId = placeId;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoPlacesListener the listener to be notified
     * @return this
     */
    public BaatoPlaces withListener(BaatoPlacesListener baatoPlacesListener) {
        this.baatoPlacesListener = baatoPlacesListener;
        return this;
    }

    public void getPlaces() {
        QueryAPI queryAPI = App.retrofitV2().create(QueryAPI.class);
        queryAPI.performPlacesQuery(giveMeQueryFilter()).enqueue(new Callback<PlaceAPIResponse>() {
            @Override
            public void onResponse(Call<PlaceAPIResponse> call, Response<PlaceAPIResponse> response) {
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
            public void onFailure(Call<PlaceAPIResponse> call, Throwable throwable) {
                baatoPlacesListener.onFailed(throwable);
            }
        });
    }

    private Map<String, String> giveMeQueryFilter() {
        Map<String, String> queryMap = new HashMap<>();
        //compulsory ones
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (placeId != 0)
            queryMap.put("placeId", placeId + "");

        return queryMap;
    }
}
