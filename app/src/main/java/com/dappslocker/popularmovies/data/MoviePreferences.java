package com.dappslocker.popularmovies.data;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.dappslocker.popularmovies.R;

public class MoviePreferences {

    private static final String PREF_POPULAR = "popular";
    private static final String PREF_TOP_RATED = "top rated";
    private static final String DEFAULT_PREF_CHOICE = PREF_POPULAR;
    private static  String PrefChoice = "";
    private Context context;

    public MoviePreferences(Context context) {
        this.context = context;
    }

    public  String getDefaultPrefChoice() {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String choice = mSharedPreferences.getString(context.getString(R.string.popular_movie_choice_list_pref_key),DEFAULT_PREF_CHOICE);
        return choice;
    }

    public  void setPrefChoice(int selectedIndex) {
        switch(selectedIndex){
            case 0:
                PrefChoice = PREF_POPULAR;
                break;
            case 1:
                PrefChoice = PREF_TOP_RATED;
                break;
            default:
                PrefChoice = getDefaultPrefChoice();
                break;
        }
    }

    public  String getPrefChoice() {
        if(PrefChoice != ""){
            return PrefChoice;
        }
        else {
            return  getDefaultPrefChoice();
        }
    }
}
