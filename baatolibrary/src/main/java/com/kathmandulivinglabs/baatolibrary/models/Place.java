package com.kathmandulivinglabs.baatolibrary.models;

import java.util.List;

public class Place {
    private Long placeId;

    private Long osmId;
    private String osmType;
    private String housenumber;
    private String address;
    private String classification;
    private String type;
    private Integer searchRank;
    private Integer addressRank;
    private String geometryType;
    private Geocode centroid;

    private List<String> tags;
    private String country;
    private Integer adminLevel;
    private String name;

    @Override
    public String toString() {
        return "Place [placeId=" + placeId + ", osmId=" + osmId + ", osmType=" + osmType + ", housenumber="
                + housenumber + ", address=" + address + ", classification=" + classification + ", type=" + type
                + ", searchRank=" + searchRank + ", addressRank=" + addressRank + ", geometryType=" + geometryType
                + ", centroid= none" + ", tags=" + tags + ", country=" + country + ", adminLevel=" + adminLevel
                + ", name=" + name + "]";
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    public String getOsmType() {
        return osmType;
    }

    public void setOsmType(String osmType) {
        this.osmType = osmType;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
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

    public String getGeometryType() {
        return geometryType;
    }

    public void setGeometryType(String geometryType) {
        this.geometryType = geometryType;
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

    public Integer getAdminLevel() {
        return adminLevel;
    }

    public void setAdminLevel(Integer adminLevel) {
        this.adminLevel = adminLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
