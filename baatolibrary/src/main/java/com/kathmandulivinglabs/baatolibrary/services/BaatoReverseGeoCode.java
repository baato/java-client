package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.Geocode;
import com.kathmandulivinglabs.baatolibrary.models.Place;
import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.List;

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
        void onSuccess(SearchAPIResponse places);

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
        queryAPI.performReverseGeoCode(accessToken, geocode.lat, geocode.lon).enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
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
            public void onFailure(Call<SearchAPIResponse> call, Throwable throwable) {
                baatoSearchRequestListener.onFailed(throwable);
            }
        });
    }
}
