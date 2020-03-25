package com.kathmandulivinglabs.baatolibrary.models;

public class AutoCompleteDataModel {
    private float placeId;
    private String name;
    private String address;
    private float score;

    // Getter Methods
    public float getPlaceId() {
        return placeId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public float getScore() {
        return score;
    }

    // Setter Methods
    public void setPlaceId(float placeId) {
        this.placeId = placeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "AutoCompleteDataModel{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", score=" + score +
                '}';
    }
}

