package com.baato.baatolibrary.services;

import android.content.Context;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.baato.baatolibrary.application.BaatoLib;
import com.baato.baatolibrary.models.ErrorResponse;
import com.baato.baatolibrary.models.SearchAPIResponse;
import com.baato.baatolibrary.requests.BaatoAPI;
import com.baato.baatolibrary.utilities.BaatoUtil;
import com.baato.baatolibrary.utilities.ErrorUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Keep
public class BaatoSearch {
    private Context context;
    private BaatoSearchRequestListener baatoSearchRequestListener;
    private String accessToken, query;
    private String type, securityCode;
    private String apiVersion = "1";
    private String apiBaseUrl = "https://api.baato.io/api/";
    private int radius = 0, limit = 0;
    private double lat = 0.00, lon = 0.00;
    private Call<SearchAPIResponse> searchAPIResponseCall;

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
     * Set the securityCode is security enabled.
     */
    public BaatoSearch setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    /**
     * Set the apiVersion. By default it takes version "1"
     */
    public BaatoSearch setAPIVersion(@NonNull String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    /**
     * Set the apiBaseURL.
     */
    public BaatoSearch setAPIBaseURL(@NonNull String apiBaseURL) {
        this.apiBaseUrl = apiBaseURL;
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

    public void doRequest() {
        BaatoAPI baatoAPI = BaatoLib.retrofitV2(apiVersion, apiBaseUrl).create(BaatoAPI.class);
        searchAPIResponseCall = baatoAPI.searchQuery(giveMeQueryFilter(context));
        searchAPIResponseCall.enqueue(new Callback<SearchAPIResponse>() {
            @Override
            public void onResponse(Call<SearchAPIResponse> call, Response<SearchAPIResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    baatoSearchRequestListener.onSuccess(response.body());
                else {
                    ErrorResponse errorResponse = ErrorUtils.parseError(response, apiVersion, apiBaseUrl);
                    baatoSearchRequestListener.onFailed(new Throwable(errorResponse.getMessage()));
                }
            }

            @Override
            public void onFailure(Call<SearchAPIResponse> call, Throwable throwable) {
                baatoSearchRequestListener.onFailed(throwable);
            }
        });
    }

    public void cancelRequest() {
        searchAPIResponseCall.cancel();
    }

    private Map<String, String> giveMeQueryFilter(Context context) {
        Map<String, String> queryMap = new HashMap<>();
        //compulsory ones
        if (accessToken != null)
            queryMap.put("key", accessToken);
        if (query != null)
            queryMap.put("q", query);

        //optionals
        if (securityCode != null && !securityCode.isEmpty())
            queryMap.put("hash", BaatoUtil.generateHash(context.getPackageName(), accessToken, securityCode));
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
