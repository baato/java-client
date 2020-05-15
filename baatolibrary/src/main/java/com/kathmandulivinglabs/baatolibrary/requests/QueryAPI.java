package com.kathmandulivinglabs.baatolibrary.requests;

import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.models.DirectionsAPIResponse;
import com.kathmandulivinglabs.baatolibrary.models.PlaceAPIResponse;

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
    Call<PlaceAPIResponse> performReverseGeoCode(@QueryMap Map<String, String> filter);

    @GET("places")
    Call<PlaceAPIResponse> performPlacesQuery(@QueryMap Map<String, String> filter);

}
