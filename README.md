![GitHub release (latest by date)](https://img.shields.io/github/v/release/baato/java-client)

# Baato-Library

The Java library makes it easy to consume the Baato API into existing native android projects.

## Features

* Search
* Reverse Geocoding
* Autocomplete
* Directions

### Getting Started

 1.Open up your project's build.gradle file. Add the following code:
 
```
allprojects{
 repositories {
  maven { url 'https://jitpack.io' }
 }
}
```

2.Open up your application's build.gradle file. Add the following code:
```
android {
 compileOptions {
        sourceCompatibility = 1.8
        targetCompatibility = 1.8
    }
 }
```

```
dependencies {
   implementation 'com.github.baato:java-client:${latest-version}'
}
```

### Prerequisites

#### To run on devices Android 9 and above,

1.Add a Network Security Configuration file
 
```
<?xml version="1.0" encoding="utf-8"?>
<manifest ... >
    <application android:networkSecurityConfig="@xml/network_security_config"
                    ... >
        ...
    </application>
</manifest>
```
2.In the res/xml/network_security_config.xml file, you can add localhost to the permitted cleartext traffic domain by adding:

```
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">178.128.59.143</domain>
    </domain-config>
</network-security-config>
```

### Implementation

 #### 1. Search 
 
```
 new BaatoSearch(this)
          .setAccessToken(YOUR_ACCESS_KEY)
          .setQuery(query)
          .setType("hospital") //optional parameter
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
```
 #### 2. Reverse GeoCode
 
 ```
  new BaatoReverseGeoCode(this)
                .setGeoCode(new Geocode(lat, lon))
                .setAccessToken(YOUR_ACCESS_KEY)
                .withListener(new BaatoReverseGeoCode.BaatoReverseGeoCodeRequestListener() {
                    @Override
                    public void onSuccess(SearchAPIResponse places) {
                        Log.d(TAG, "onSuccess: reverse " + places.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                     
                    }
                })
                .doReverseGeoCode();
```
#### 3. Autocomplete
 
 ```
  new BaatoAutoComplete(this)
                .setAccessToken(YOUR_ACCESS_KEY)
                .setQuery(query)
                .setLimit(5) //optional parameter
                .withListener(new BaatoAutoComplete.BaatoAutoCompleteListener() {
                    @Override
                    public void onSuccess(AutoCompleteAPIResponse places) {
                        Log.d(TAG, "onSuccess: autocomplete" + places.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                      
                    }
                })
                .doAutoComplete();
```
#### 4. Directions
 
 ```
    String points[] = new String[]{"27.73405,85.33685", "27.7177,85.3278"};
        new BaatoNavigationRoute(this)
                .setPoints(points)
                .setAccessToken(YOUR_ACCESS_KEY)
                .setMode(mode) //eg bike, car, foot
                .setAlternatives(false) //optional parameter
                .setInstructions(true) //optional parameter
                .withListener(new BaatoNavigationRoute.BaatoRouteRequestListener() {
                    @Override
                    public void onSuccess(DirectionsAPIResponse directionResponse) {
                        // success response here
                        Log.d(TAG, "onSuccess: routes" + directionResponse.toString());
                    }

                    @Override
                    public void onFailed(Throwable error) {
                     
                    }
                })
                .doRequest();
    }
```
#### 5. To use turn by turn navigation:

 Get the directionResponse from step no 4 and follow the below steps:
 
 ```
 NavResponse navResponse = directionResponse.getData().get(0);
 ObjectNode obj = NavigateResponseConverter.convertFromGHResponse(navResponse, Locale.ENGLISH, new  DistanceConfig(DistanceUtils.Unit.METRIC, translationMap, navigateResponseConverterTranslationMap, Locale.ENGLISH));  
 DirectionsResponse directionsResponse = DirectionsResponse.fromJson(obj.toString());
 currentRoute = directionsResponse.routes().get(0);
```
Now that you have your route, you can navigate using NavigationLauncher

```
boolean simulateRoute=false;
NavigationLauncherOptions options = NavigationLauncherOptions.builder()
        .directionsRoute(currentRoute)
        .shouldSimulateRoute(simulateRoute) // boolean value set true for simulation
        .build();
NavigationLauncher.startNavigation(YourActivity.this, options);
```

## Built With

* [Retrofit](https://github.com/square/retrofit) - Used to handle API requests
* [Maven](https://maven.apache.org/) - Dependency Management
* [Graphhopper](https://github.com/graphhopper/graphhopper) - Used to Handle navigation API response
