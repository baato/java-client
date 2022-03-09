package com.baato.baatolibrary.models;

import java.util.List;

public class Place {
    private String license;

    private Object score;

    private String address;

    private LatLon centroid;

    private int placeId;

    private long osmId;

    private String name;

    private Geometry geometry;

    private String type;

    private List<String> tags;

    public long getOsmId() {
        return osmId;
    }

    public void setOsmId(long osmId) {
        this.osmId = osmId;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Object getScore() {
        return score;
    }

    public void setScore(Object score) {
        this.score = score;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLon getCentroid() {
        return centroid;
    }

    public void setCentroid(LatLon centroid) {
        this.centroid = centroid;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Place{" +
                "license='" + license + '\'' +
                ", score='" + score + '\'' +
                ", address='" + address + '\'' +
                ", centroid=" + centroid +
                ", placeId='" + placeId + '\'' +
                ", osmId='" + osmId + '\'' +
                ", name='" + name + '\'' +
                ", geometry=" + geometry +
                ", type='" + type + '\'' +
                ", tags=" + tags +
                '}';
    }
}
