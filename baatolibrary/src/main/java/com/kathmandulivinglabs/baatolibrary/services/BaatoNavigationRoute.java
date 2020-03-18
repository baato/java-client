package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.Place;
import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoNavigationRoute {
    private Context context;
    private BaatoRouteRequestListener baatoRouteRequestListener;
    private String accessToken, query;

    public interface BaatoRouteRequestListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(SearchAPIResponse places);

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
     * Set the query to search.
     */
    public BaatoNavigationRoute setQuery(@NonNull String query) {
        this.query = query;
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

    public void giveMeRoutes() {
        QueryAPI queryAPI = App.retrofit(accessToken).create(QueryAPI.class);
        queryAPI.searchQuery(accessToken, query).enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoRouteRequestListener.onSuccess(response.body());
                else{
                    try {
                        baatoRouteRequestListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchAPIResponse> call, Throwable throwable) {
                baatoRouteRequestListener.onFailed(throwable);
            }
        });
    }
}
