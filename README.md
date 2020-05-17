![GitHub release (latest by date)](https://img.shields.io/github/v/release/baato/java-client)

# Baato-Library

The Java library makes it easy to consume the Baato API into existing native android projects.

## Features

* Search
* Reverse Geocoding
* Places
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
        <domain includeSubdomains="true">baato.io</domain>
    </domain-config>
</network-security-config>
```

### Implementation

 #### 1. Search 
 
```
 new BaatoSearch(this)
          .setAccessToken(YOUR_ACCESS_KEY)
          .setAPIVersion("1") // optional, default will be "1" if not set
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
           .doRequest();
```
 #### 2. Reverse GeoCode
 
 ```
   new BaatoReverse(this)
                .setLatLon(new LatLon(lat, lon))
                .setAccessToken(YOUR_ACCESS_KEY)
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
```
#### 3. Places
 
 ```
 new BaatoPlace(this)
                .setAccessToken(YOUR_ACCESS_KEY)
                .setPlaceId(placeId)
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
```
#### 4. Directions
 
 ```
    String points[] = new String[]{"27.73405,85.33685", "27.7177,85.3278"};
        new BaatoRouting(this)
                .setPoints(points)
                .setAccessToken(YOUR_ACCESS_KEY)
                .setMode(mode) //eg bike, car, foot
                .setAlternatives(false) //optional parameter
                .setInstructions(true) //optional parameter
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
```
#### 5. To use turn by turn navigation:

 Get the currentRoute from step no 4 and follow the below steps:
 
 ```
 String parsedNavigationResponse = BaatoNavigationRoute.getParsedNavResponse(directionResponse, navigationMode);
 DirectionsResponse directionsResponse = DirectionsResponse.fromJson(parsedNavigationResponse);
 DirectionsRoute currentRoute = directionsResponse.routes().get(0);
```
#### 6. Baato Navigation SDK

Now that you have your route, you can use the [Baato Navigation SDK](https://github.com/baato/navigation-sdk) for navigation.

Add the following dependencies to build.gradle in your android project

```
// baato navigation SDK
dependencies {
  implementation 'com.github.baato.navigation-sdk:baato-navigation-android:${latest-version}'
  implementation 'com.github.baato.navigation-sdk:baato-navigation-android-ui:${latest-version}'
}
```
You can now launch the navigation UI and navigate through your app. 

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
