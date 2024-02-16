package com.baato.baatolibrary.requests;

import com.baato.baatolibrary.models.DirectionsAPIResponse;
import com.baato.baatolibrary.models.PlaceAPIResponse;
import com.baato.baatolibrary.models.SearchAPIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BaatoAPI {
    @GET("directions")
    Call<DirectionsAPIResponse> getDirections(
            @Header("SessionId") String sessionId,
            @Header("BundleIdentifier") String bundleIdentifier,
            @QueryMap Map<String, Object> filters, @Query("points[]") String[] points);

    @GET("search")
    Call<SearchAPIResponse> searchQuery(
            @Header("SessionId") String sessionId,
            @Header("BundleIdentifier") String bundleIdentifier,
            @QueryMap Map<String, String> filters);

    @GET("reverse")
    Call<PlaceAPIResponse> performReverseGeoCode(
            @Header("SessionId") String sessionId,
            @Header("BundleIdentifier") String bundleIdentifier,
            @QueryMap Map<String, String> filter);

    @GET("places")
    Call<PlaceAPIResponse> performPlacesQuery(
            @Header("SessionId") String sessionId,
            @Header("BundleIdentifier") String bundleIdentifier,
            @QueryMap Map<String, String> filter);

}
