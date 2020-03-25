package com.kathmandulivinglabs.baatolibrary.models;

public class Geocode {
    public double lat,lon;

    public Geocode(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Geocode{" +
                "lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
