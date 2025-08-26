package com.baato.baatolibrary.models;

import androidx.annotation.NonNull;

public class LatLon {
    public double lat,lon;

    public LatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @NonNull
    @Override
    public String toString() {
        return "LatLon{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
