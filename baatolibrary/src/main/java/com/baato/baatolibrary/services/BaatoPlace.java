package com.baato.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.baato.baatolibrary.application.App;
import com.baato.baatolibrary.models.PlaceAPIResponse;
import com.baato.baatolibrary.requests.BaatoAPI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoPlace {

    private Context context;
    private String accessToken;
    private String apiVersion = "v1";
    private String apiBaseUrl = "https://api.baato.io/api/";
    private BaatoPlaceListener baatoPlaceListener;
    private int placeId = 0;

    public interface BaatoPlaceListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(PlaceAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoPlace(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoPlace setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the placeId.
     */
    public BaatoPlace setPlaceId(@NonNull int placeId) {
        this.placeId = placeId;
        return this;
    }

    /**
     * Set the apiVersion. By default it takes version "1"
     */
    public BaatoPlace setAPIVersion(@NonNull String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * Set the apiBaseURL.
     */
    public BaatoPlace setAPIBaseURL(@NonNull String apiBaseURL) {
        this.apiBaseUrl = apiBaseURL;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoPlaceListener the listener to be notified
     * @return this
     */
    public BaatoPlace withListener(BaatoPlaceListener baatoPlaceListener) {
        this.baatoPlaceListener = baatoPlaceListener;
        return this;
    }

    public void doRequest() {
        BaatoAPI baatoAPI = App.retrofitV2(apiVersion, apiBaseUrl).create(BaatoAPI.class);
        baatoAPI.performPlacesQuery(giveMeQueryFilter()).enqueue(new Callback<PlaceAPIResponse>() {
            @Override
            public void onResponse(Call<PlaceAPIResponse> call, Response<PlaceAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoPlaceListener.onSuccess(response.body());
                else {
                    try {
                        baatoPlaceListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceAPIResponse> call, Throwable throwable) {
                baatoPlaceListener.onFailed(throwable);
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
