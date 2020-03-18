package com.kathmandulivinglabs.routelibrary;

import com.kathmandulivinglabs.routelibrary.models.Geometry;

import java.util.ArrayList;
import java.util.List;

public class BaatoUtil {
    public static Geometry getGeoJsonFromEncodedPolyLine(String encoded) {
        return new Geometry("LineString", decodePolyline(encoded, false));
    }

    public static List<List<Double>> decodePolyline(String encoded, boolean is3D) {
        List<List<Double>> pointList = new ArrayList<>();
        int index = 0;
        int len = encoded.length();
        int lat = 0, lng = 0, ele = 0;
        while (index < len) {
            // latitude
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLatitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += deltaLatitude;

            // longitute
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int deltaLongitude = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += deltaLongitude;

            if (is3D) {
                // elevation
                shift = 0;
                result = 0;
                do {
                    b = encoded.charAt(index++) - 63;
                    result |= (b & 0x1f) << shift;
                    shift += 5;
                } while (b >= 0x20);
                int deltaElevation = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
                ele += deltaElevation;
                List<Double> list = new ArrayList<>();
                list.add((double) lat / 1e5);
                list.add((double) lng / 1e5);
                list.add((double) ele / 100);
                pointList.add(list);
            } else {
                List<Double> list = new ArrayList<>();
                list.add((double) lat / 1e5);
                list.add((double) lng / 1e5);
                pointList.add(list);
            }
        }
        return pointList;
    }


}
