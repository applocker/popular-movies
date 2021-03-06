package com.dappslocker.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

class MoviePreferences {
    private static final String PREF_POPULAR = "popular";
    private static final String PREF_TOP_RATED = "top rated";

    public static String getPrefFavourite() {
        return PREF_FAVOURITE;
    }

    private static final String PREF_FAVOURITE = "favourites";
    private static final String DEFAULT_PREF_CHOICE = PREF_POPULAR;
    private static  String PrefChoice = "";

    public  static String getDefaultPrefChoice(Context context) {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return mSharedPreferences.getString(context.getString(R.string.popular_movie_choice_list_pref_key),DEFAULT_PREF_CHOICE);
    }

    public static void setPrefChoice(int selectedIndex, Context context) {
        switch(selectedIndex){
            case 0:
                PrefChoice = PREF_POPULAR;
                break;
            case 1:
                PrefChoice = PREF_TOP_RATED;
                break;
            case 2:
                PrefChoice = PREF_FAVOURITE;
                break;
            default:
                PrefChoice = getDefaultPrefChoice(context);
                break;
        }
    }

    public  static String getPrefChoice(Context context) {
        PrefChoice = getDefaultPrefChoice(context);
        if(!PrefChoice.equals("")){
            return PrefChoice;
        }
        else {
            return  getDefaultPrefChoice(context);
        }
    }
}
