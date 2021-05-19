package com.baato.baatolibrary.requests;

import com.baato.baatolibrary.models.DirectionsAPIResponse;
import com.baato.baatolibrary.models.PlaceAPIResponse;
import com.baato.baatolibrary.models.SearchAPIResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BaatoAPI {
    @GET("directions")
    Call<DirectionsAPIResponse> getDirections(@QueryMap Map<String, Object> filters, @Query("points[]") String[] points);

    @GET("search")
    Call<SearchAPIResponse> searchQuery(@QueryMap Map<String, String> filters);

    @GET("reverse")
    Call<PlaceAPIResponse> performReverseGeoCode(@QueryMap Map<String, String> filter);

    @GET("places")
    Call<PlaceAPIResponse> performPlacesQuery(@QueryMap Map<String, String> filter);

}
