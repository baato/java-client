package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.DirectionsAPIResponse;
import com.kathmandulivinglabs.baatolibrary.models.Place;
import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoNavigationRoute {
    private Context context;
    private BaatoRouteRequestListener baatoRouteRequestListener;
    private String accessToken, query, mode;
    private String[] points;
    private Boolean alternatives;
    private Boolean instructions;

    public interface BaatoRouteRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(DirectionsAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoNavigationRoute(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoNavigationRoute setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the mode.
     */
    public BaatoNavigationRoute setMode(@NonNull String mode) {
        this.mode = mode;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoNavigationRoute setAlternatives(@NonNull Boolean alternatives) {
        this.alternatives = alternatives;
        return this;
    }

    /**
     * Set true to get instruction list
     */
    public BaatoNavigationRoute setInstructions(Boolean instructions) {
        this.instructions = instructions;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoNavigationRoute setPoints(@NonNull String[] points) {
        this.points = points;
        return this;
    }



    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoRouteRequestListener the listener to be notified
     * @return this
     */
    public BaatoNavigationRoute withListener(BaatoRouteRequestListener baatoRouteRequestListener) {
        this.baatoRouteRequestListener = baatoRouteRequestListener;
        return this;
    }

    public void doRequest() {
        QueryAPI queryAPI = App.retrofitV2().create(QueryAPI.class);
        queryAPI.getDirections(accessToken,points,mode,alternatives,instructions)
                .enqueue(new Callback<DirectionsAPIResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsAPIResponse> call, Response<DirectionsAPIResponse> response) {
                        baatoRouteRequestListener.onFailed(new Throwable(String.valueOf(call.request())));
                        if (response.isSuccessful() && response.body() != null)
                            baatoRouteRequestListener.onSuccess(response.body());
                        else {
                            try {
                                baatoRouteRequestListener.onFailed(new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsAPIResponse> call, Throwable t) {
                        baatoRouteRequestListener.onFailed(t);
                    }
                });
    }
}
