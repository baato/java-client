package com.kathmandulivinglabs.routelibrary.models;

import java.lang.reflect.Array;
import java.util.List;

public class Geometry {
    public String type;
    public Object coordinates;

    public Geometry(String type, Object coordinates) {
        this.type = type;
        this.coordinates = coordinates;
    }
}
