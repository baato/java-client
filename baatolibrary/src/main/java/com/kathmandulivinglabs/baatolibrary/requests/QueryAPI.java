package com.kathmandulivinglabs.baatolibrary.requests;

import com.kathmandulivinglabs.baatolibrary.models.AutoCompleteAPIResponse;
import com.kathmandulivinglabs.baatolibrary.models.DirectionsAPIResponse;
import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface QueryAPI {
    @GET("directions")
    Call<DirectionsAPIResponse> getDirections(@Query("key") String key, @Query("points[]") String[] points, @Query("mode") String mode, @Query("alternatives") Boolean alternatives,@Query("instructions") Boolean instructions);

    @GET("search")
    Call<SearchAPIResponse> searchQuery(@QueryMap Map<String, String> filters);

    @GET("reverse")
    Call<SearchAPIResponse> performReverseGeoCode(@Query("key") String key, @Query("lat") double lat, @Query("lon") double lon);

    @GET("autocomplete")
    Call<AutoCompleteAPIResponse> performAutoComplete(@QueryMap Map<String, String> filter);

}
