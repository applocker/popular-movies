package com.dappslocker.popularmovies.data;

/**
 * Created by Tiwuya on 06/07/2018.
 */

public class MoviePreferences {

    public static final String PREF_POPULAR = "popular";
    public static final String PREF_TOP_RATED = "toprated";
    private static final String DEFAULT_PREF_CHOICE = PREF_POPULAR;
    private static  String PrefChoice;
    public static String getDefaultPrefChoice() {
        return DEFAULT_PREF_CHOICE;
    }


    public static void setPrefChoice(int selectedIndex) {
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

    public static String getPrefChoice() {
        if(PrefChoice != null){
            return PrefChoice;
        }
        else {
            return  getDefaultPrefChoice();
        }
    }
}
