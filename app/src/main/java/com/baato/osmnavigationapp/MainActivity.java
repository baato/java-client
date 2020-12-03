package com.baato.osmnavigationapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.baato.baatolibrary.models.SearchAPIResponse;
import com.baato.baatolibrary.models.DirectionsAPIResponse;
import com.baato.baatolibrary.models.LatLon;
import com.baato.baatolibrary.models.Geometry;

import com.baato.baatolibrary.models.PlaceAPIResponse;
import com.baato.baatolibrary.navigation.BaatoTranslationMap;
import com.baato.baatolibrary.services.BaatoPlace;
import com.baato.baatolibrary.services.BaatoRouting;
import com.baato.baatolibrary.services.BaatoReverse;
import com.baato.baatolibrary.services.BaatoSearch;
import com.baato.baatolibrary.services.ToasterMessage;
import com.baato.baatolibrary.utilities.BaatoUtil;
import com.kathmandulivinglabs.osmnavigationapp.R;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "apple";
    String encoded = "wv{gD_`lhOQvAO^c@h@Yj@IZKhADXj@|ABrAElB@NLLp@LHFDJJvDb@pBx@~B`AjBBP@fCArBW|KSxEQbHGbAYhBKVmA`C}A`EuBrGcAxC}@hBA^BJb@`@dBr@tBbAh@RdDbBs@fCYxAmBnKy@`F_Gj[]zBS~AWjDC~AA|BBbBj@|JpB~YRrDbAjONlDD~AAbCuArj@o@nSkB~w@CzB{@x\\e@rLa@jN@zBO|DE|EQjBKp@W~@Sf@o@dAs@x@i@d@_@RkAj@_AP_AJmCDkBD}IDoFF_ETc@@iARyAf@UKa@_@YKk@KuBGu@Kq@MkA[k@SsB{@wCwAgAo@g@SsDiBa@OcJmE{IcEiAm@kJmEmAc@U?QA[IUK]EmI_@wBCk@Dm@FiCt@k@ViNvJ_BlAcAj@{@Xc@JuALsGN{@F_@Da@NeAn@WXWb@]fAIf@Al@Bt@`AvKVhDDpAMjB[jASb@yAzBiAvAuCfEYxAe@SmE}AsGeA_FiBwG_CsB_@sGq@{AWcA]eASuC{@g@IiDs@_BWy@FiG`BqCx@sFvA{JlC_APq@DqA?[CcAOgCg@qFqAgGkA{@Gk@@i@Dg@Je@NmAl@_^~R^p@lAlAdAv@^T~Al@t@l@F?b@MjChANBl@ATIDCFl@APNH?Ho@`CTf@HJ`@FJN?Je@fA[d@CJBJHJ@POv@HPFVd@ZBJAJa@v@bAf@JjAw@`@yAf@s@d@SHQAAf@c@C@p@YAm@R?n@SPkAPAB?|@g@HCLaAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToasterMessage.s(this, "Hello Good Morning");
        Geometry geometry = BaatoUtil.getGeoJsonFromEncodedPolyLine(encoded);
        BaatoTranslationMap trMap = new BaatoTranslationMap().doImport(getApplicationContext());
        performRouting();
        performReverseGeoCoding();
        performSearch();
        getPlaces();
    }

    private void performRouting() {
        String points[] = new String[]{"27.73405,85.33685", "27.7177,85.3278"};
        new BaatoRouting(this)
                .setPoints(points)
                .setAccessToken(Constants.TOKEN)
                .setMode("foot")
                .setAlternatives(false)
                .setInstructions(true)
                .withListener(new BaatoRouting.BaatoRoutingRequestListener() {
                    @Override
                    public void onSuccess(DirectionsAPIResponse directionResponse) {
                        // success response here
                        Log.d(TAG, "onSuccess: routes" + directionResponse.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                        // failure response here
                        Log.d(TAG, "onFailed:routes " + error.getMessage());
                    }
                })
                .doRequest();
    }

    private void performReverseGeoCoding() {
        new BaatoReverse(this)
                .setLatLon(new LatLon(27.73405, 85.33685))
                .setAPIVersion("1")
                .setAccessToken(Constants.TOKEN)
                .setLimit(5)
                .withListener(new BaatoReverse.BaatoReverseRequestListener() {
                    @Override
                    public void onSuccess(PlaceAPIResponse places) {
                        // success response here
                        Log.d(TAG, "onSuccess: reverse " + places.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                        // failure response here
                        Log.d(TAG, "onFailed:reverse " + error.getMessage());
                    }
                })
                .doRequest();
    }

    private void getPlaces() {
        new BaatoPlace(this)
                .setAccessToken(Constants.TOKEN)
                .setPlaceId(101499)
                .withListener(new BaatoPlace.BaatoPlaceListener() {
                    @Override
                    public void onSuccess(PlaceAPIResponse place) {
                        //success response here
                        Log.d(TAG, "onSuccess: place" + place.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                        //failure response here
                        Log.d(TAG, "onFailed: place" + error.getMessage());
                    }
                })
                .doRequest();
    }

    private void performSearch() {
        new BaatoSearch(this)
                .setAccessToken(Constants.TOKEN)
                .setAPIVersion("1")
                .setQuery("Kathmandu")
                .setLimit(5)
                .withListener(new BaatoSearch.BaatoSearchRequestListener() {
                    @Override
                    public void onSuccess(SearchAPIResponse places) {
                        // get the list of search results here
                        Log.d(TAG, "onSuccess:search " + places.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                        // get the error messages here
                        Log.d(TAG, "onFailed:search " + error.getMessage());
                    }
                })
                .doRequest();
    }

}
