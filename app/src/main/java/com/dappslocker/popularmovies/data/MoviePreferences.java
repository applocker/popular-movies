package com.dappslocker.popularmovies.data;

/**
 * Created by Tiwuya on 06/07/2018.
 */

public class MoviePreferences {

    public static final String PREF_POPULAR = "popular";
    public static final String PREF_TOP_RATED = "toprated";
    private static final String DEFAULT_PREF_CHOICE = PREF_POPULAR;
    public static String getDefaultPrefChoice() {
        return DEFAULT_PREF_CHOICE;
    }
}
