package com.baato.baatolibrary.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.baato.baatolibrary.models.Geometry;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaatoUtil {
    private static final String PREFS_NAME = "baato_sdk";
    private static final String KEY_USER_ID = "baato_user_id";

    public static Geometry getGeoJsonFromEncodedPolyLine(String encoded) {
        return new Geometry("LineString", decodePolyline(encoded, false));
    }

    public static String generateHash(String packageName, String accessToken, String secret) {
        byte[] key = secret.getBytes();

        HmacUtils hm256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_512, key);
        // hm256 object can be used again and again
        return hm256.hmacHex(packageName+accessToken);
    }

    /**
     * Builds the appId identifying the host application, as Android_&lt;packageName&gt;_&lt;versionName&gt;.
     * Falls back to Android_&lt;packageName&gt; if the version name is unavailable.
     */
    public static String getAppId(Context context) {
        String packageName = context.getPackageName();
        try {
            String versionName = context.getPackageManager()
                    .getPackageInfo(packageName, 0).versionName;
            if (versionName != null && !versionName.isEmpty())
                return "Android_" + packageName + "_" + versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
            // fall through to the package name only form
        }
        return "Android_" + packageName;
    }

    /**
     * Returns an anonymous identifier for this app installation, so repeated requests can be
     * recognised as coming from the same user. Generated once and persisted, so it stays the same
     * across launches. It is reset only when the app is reinstalled or its data is cleared.
     */
    public static synchronized String getUserId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(KEY_USER_ID, null);
        if (userId == null || userId.isEmpty()) {
            userId = UUID.randomUUID().toString();
            prefs.edit().putString(KEY_USER_ID, userId).apply();
        }
        return userId;
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
