package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.DirectionsAPIResponse;

import com.kathmandulivinglabs.baatolibrary.navigation.NavigateResponseConverter;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class BaatoRouting {
    private Context context;
    private BaatoRoutingRequestListener baatoRoutingRequestListener;
    private String accessToken, query, mode;
    private String apiVersion = "1";
    private String[] points;
    private Boolean alternatives;
    private Boolean instructions;


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
        QueryAPI queryAPI = App.retrofitV2(apiVersion).create(QueryAPI.class);
        queryAPI.getDirections(accessToken, points, mode, alternatives, instructions)
                .enqueue(new Callback<DirectionsAPIResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsAPIResponse> call, Response<DirectionsAPIResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            baatoRoutingRequestListener.onSuccess(response.body());
                        } else {
                            try {
                                baatoRoutingRequestListener.onFailed(new Throwable(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<DirectionsAPIResponse> call, Throwable t) {
                        baatoRoutingRequestListener.onFailed(t);
                    }
                });

    }

    public static String getParsedNavResponse(DirectionsAPIResponse response, String mode) {
        return NavigateResponseConverter.convertFromGHResponse(response.getData().get(0), mode).toString();
    }
}
