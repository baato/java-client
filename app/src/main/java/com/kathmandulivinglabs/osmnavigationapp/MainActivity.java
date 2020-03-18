package com.kathmandulivinglabs.osmnavigationapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.kathmandulivinglabs.baatolibrary.models.Geocode;
import com.kathmandulivinglabs.baatolibrary.models.Geometry;

import com.kathmandulivinglabs.baatolibrary.models.SearchAPIResponse;
import com.kathmandulivinglabs.baatolibrary.services.BaatoReverseGeoCode;
import com.kathmandulivinglabs.baatolibrary.services.BaatoSearch;
import com.kathmandulivinglabs.baatolibrary.services.ToasterMessage;
import com.kathmandulivinglabs.baatolibrary.utilities.BaatoUtil;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "apple";
    String encoded = "wv{gD_`lhOQvAO^c@h@Yj@IZKhADXj@|ABrAElB@NLLp@LHFDJJvDb@pBx@~B`AjBBP@fCArBW|KSxEQbHGbAYhBKVmA`C}A`EuBrGcAxC}@hBA^BJb@`@dBr@tBbAh@RdDbBs@fCYxAmBnKy@`F_Gj[]zBS~AWjDC~AA|BBbBj@|JpB~YRrDbAjONlDD~AAbCuArj@o@nSkB~w@CzB{@x\\e@rLa@jN@zBO|DE|EQjBKp@W~@Sf@o@dAs@x@i@d@_@RkAj@_AP_AJmCDkBD}IDoFF_ETc@@iARyAf@UKa@_@YKk@KuBGu@Kq@MkA[k@SsB{@wCwAgAo@g@SsDiBa@OcJmE{IcEiAm@kJmEmAc@U?QA[IUK]EmI_@wBCk@Dm@FiCt@k@ViNvJ_BlAcAj@{@Xc@JuALsGN{@F_@Da@NeAn@WXWb@]fAIf@Al@Bt@`AvKVhDDpAMjB[jASb@yAzBiAvAuCfEYxAe@SmE}AsGeA_FiBwG_CsB_@sGq@{AWcA]eASuC{@g@IiDs@_BWy@FiG`BqCx@sFvA{JlC_APq@DqA?[CcAOgCg@qFqAgGkA{@Gk@@i@Dg@Je@NmAl@_^~R^p@lAlAdAv@^T~Al@t@l@F?b@MjChANBl@ATIDCFl@APNH?Ho@`CTf@HJ`@FJN?Je@fA[d@CJBJHJ@POv@HPFVd@ZBJAJa@v@bAf@JjAw@`@yAf@s@d@SHQAAf@c@C@p@YAm@R?n@SPkAPAB?|@g@HCLaAB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToasterMessage.s(this, "Hello Good Morning");
        Geometry geometry = BaatoUtil.getGeoJsonFromEncodedPolyLine(encoded);

        performReverseGeoCoding();
        performSearch();
    }

    private void performReverseGeoCoding() {
        new BaatoReverseGeoCode(this)
                .setGeoCode(new Geocode(27.73405, 85.33685))
                .setAccessToken(Constants.TOKEN)
                .withListener(new BaatoReverseGeoCode.BaatoReverseGeoCodeRequestListener() {
                    @Override
                    public void onSuccess(SearchAPIResponse places) {
                        // success response here
                        Log.d(TAG, "onSuccess: reverse " + places.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                        // failure response here
                        Log.d(TAG, "onFailed:reverse " + error.getMessage());
                    }
                })
                .doReverseGeoCode();
    }

    private void performSearch() {
        new BaatoSearch(this)
                .setAccessToken(Constants.TOKEN)
                .setQuery("Kathmandu Living Labs")
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
                .doSearch();
    }

}
