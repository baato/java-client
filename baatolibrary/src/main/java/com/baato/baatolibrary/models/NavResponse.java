package com.baato.baatolibrary.models;

import com.baato.baatolibrary.navigation.InstructionResponse;

import java.util.List;

public class NavResponse {

    private String encodedPolyline;
    private double routeWeight;
    private double distanceInMeters;
    private long timeInMs;
    private List<InstructionResponse> instructionList = null;

    public String getEncoded_polyline () {
        return encodedPolyline;
    }

    public void setEncoded_polyline (String encoded_polyline){
        this.encodedPolyline = encoded_polyline;
    }

    public double getRouteWeight() {
        return routeWeight;
    }

    public void setRouteWeight(double routeWeight) {
        this.routeWeight = routeWeight;
    }

    public double getDistanceInMeters() {
        return distanceInMeters;
    }

    public void setDistanceInMeters(double distanceInMeters) {
        this.distanceInMeters = distanceInMeters;
    }

    public long getTimeInMs () {
        return timeInMs;
    }

    public void setTimeInMs ( long timeInMs){
        this.timeInMs = timeInMs;
    }

    public List<InstructionResponse> getInstructionList () {
        return instructionList;
    }

    public void setInstructionList (List <InstructionResponse> instructionList) {
        this.instructionList = instructionList;
    }

    public NavResponse(String encoded_polyline, double distanceInMeters, long timeInMs, List<
            InstructionResponse> instructionList){
        this.encodedPolyline = encoded_polyline;
        this.distanceInMeters = distanceInMeters;
        this.timeInMs = timeInMs;
        this.instructionList = instructionList;
    }

    public NavResponse() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString () {
        return "NavigationResponse [encoded_polyline=" + encodedPolyline + ", distance=" + distanceInMeters + ", timeInMs="
                + timeInMs + ", instructionList=" + instructionList + "]";
    }
}

