package com.kathmandulivinglabs.baatolibrary.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.kathmandulivinglabs.baatolibrary.application.App;
import com.kathmandulivinglabs.baatolibrary.models.AutoCompleteAPIResponse;
import com.kathmandulivinglabs.baatolibrary.requests.QueryAPI;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaatoAutoComplete {

    private Context context;
    private String accessToken, query;
    private BaatoAutoCompleteListener baatoAutoCompleteListener;

    public interface BaatoAutoCompleteListener {
        /**
         * onSuccess method called after it is successful
         * onFailed method called if it can't places
         */
        void onSuccess(AutoCompleteAPIResponse places);

        void onFailed(Throwable error);
    }

    public BaatoAutoComplete(Context context) {
        this.context = context;
    }

    /**
     * Set the accessToken.
     */
    public BaatoAutoComplete setAccessToken(@NonNull String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    /**
     * Set the query to search.
     */
    public BaatoAutoComplete setQuery(@NonNull String query) {
        this.query = query;
        return this;
    }

    /**
     * Method to set the UpdateListener for the AppUpdaterUtils actions
     *
     * @param baatoAutoCompleteListener the listener to be notified
     * @return this
     */
    public BaatoAutoComplete withListener(BaatoAutoComplete.BaatoAutoCompleteListener baatoAutoCompleteListener) {
        this.baatoAutoCompleteListener = baatoAutoCompleteListener;
        return this;
    }

    public void performAutoComplete(){
        QueryAPI queryAPI = App.retrofit(accessToken).create(QueryAPI.class);
        queryAPI.performAutoComplete(accessToken, query).enqueue(new Callback<AutoCompleteAPIResponse>() {
            @Override
            public void onResponse(Call<AutoCompleteAPIResponse> call, Response<AutoCompleteAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoAutoCompleteListener.onSuccess(response.body());
                else{
                    try {
                        baatoAutoCompleteListener.onFailed(new Throwable(response.errorBody().string()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AutoCompleteAPIResponse> call, Throwable throwable) {
                baatoAutoCompleteListener.onFailed(throwable);
            }
        });
    }
}
