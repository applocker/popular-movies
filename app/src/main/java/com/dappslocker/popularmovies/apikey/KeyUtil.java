package com.dappslocker.popularmovies.apikey;


import com.dappslocker.popularmovies.BuildConfig;

public class KeyUtil {
    public static String getApiKey() {
        return BuildConfig.ApiKey;
    }
}
