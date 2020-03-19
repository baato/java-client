![GitHub release (latest by date)](https://img.shields.io/github/v/release/baato/java-client)

# Baato-Library

Fast and efficient library to perform search and reverse geocoding

## Features

* Search
* Reverse Geocoding
* Navigation

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
   implementation 'com.github.baato:java-client:{latest-version}'
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
          .setAccessToken(Constants.TOKEN)
          .setQuery(query)
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
```

## Built With

* [Retrofit](https://github.com/square/retrofit) - Used to handle API requests
* [Maven](https://maven.apache.org/) - Dependency Management
