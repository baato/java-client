package com.baato.baatolibrary.models;


public class SearchDataModel {
    private int placeId;
    private String name;
    private String address;
    private double score, radialDistanceInKm;
    private String type;

    // Getter Methods
    public int getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getScore() {
        return score;
    }

    // Setter Methods
    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getRadialDistanceInKm() {
        return radialDistanceInKm;
    }

    public void setRadialDistanceInKm(double radialDistanceInKm) {
        this.radialDistanceInKm = radialDistanceInKm;
    }

    @Override
    public String toString() {
        return "SearchDataModel{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                ", radialDistanceInKm='" + radialDistanceInKm + '\'' +
                '}';
    }
}

