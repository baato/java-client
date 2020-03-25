package com.kathmandulivinglabs.baatolibrary.models;

import java.util.List;

public class Place {
    private Long placeId;
    private String osmType;
    private String address;
    private String classification;
    private String type;
    private Integer searchRank;
    private Integer addressRank;
    private Geocode centroid;
    private String license;
    private List<String> tags;
    private String country;
    private String name;

    @Override
    public String toString() {
        return "Place{" +
                "placeId=" + placeId +
                ", osmType='" + osmType + '\'' +
                ", address='" + address + '\'' +
                ", classification='" + classification + '\'' +
                ", type='" + type + '\'' +
                ", searchRank=" + searchRank +
                ", addressRank=" + addressRank +
                ", centroid=" + centroid.toString() +
                ", license='" + license + '\'' +
                ", tags=" + tags +
                ", country='" + country + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSearchRank() {
        return searchRank;
    }

    public void setSearchRank(Integer searchRank) {
        this.searchRank = searchRank;
    }

    public Integer getAddressRank() {
        return addressRank;
    }

    public void setAddressRank(Integer addressRank) {
        this.addressRank = addressRank;
    }

    public Geocode getCentroid() {
        return centroid;
    }

    public void setCentroid(Geocode centroid) {
        this.centroid = centroid;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
