package com.kathmandulivinglabs.baatolibrary.models;

import androidx.annotation.NonNull;

import java.util.List;

public class SearchAPIResponse {

    private String timestamp;
    private Integer status;
    private String message;
    private List<Place> data;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Place> getData() {
        return data;
    }

    public void setData(List<Place> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchAPIResponse{" +
                "timestamp='" + timestamp + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
