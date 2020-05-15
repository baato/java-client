package com.kathmandulivinglabs.baatolibrary.models;

public class SearchDataModel {
    private float placeId;
    private String name;
    private String address;
    private float score;
    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SearchDataModel{" +
                "placeId=" + placeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", score=" + score +
                ", type='" + type + '\'' +
                '}';
    }
}

