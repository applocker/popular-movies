package com.dappslocker.popularmovies;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.dappslocker.popularmovies.data.MoviePreferences;

/**
 * Created by Tiwuya on 08/07/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SettingsActivity sActivity;
    private ListPreference mListPreference;
    private SharedPreferences mSharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(sActivity);
        //trigger the event to set the initial summary
        onSharedPreferenceChanged(mSharedPreferences, getString(R.string.popular_movie_choice_list_pref_key));

    }

    @Override
    public void onAttach(Context activity){
        super.onAttach(activity.getApplicationContext());
        sActivity = (SettingsActivity)activity;//get a reference to the activity that owns this fragment
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.popular_movie_choice_list_pref_key))) {
            mListPreference = (ListPreference)findPreference(key);
            int selectedIndex = -1;
            selectedIndex = mListPreference.findIndexOfValue(sharedPreferences.getString(key, MoviePreferences.getDefaultPrefChoice()));
            if (selectedIndex > -1) {
                findPreference(key).setSummary(mListPreference.getEntries()[selectedIndex]);
                //set the users preference
                MoviePreferences.setPrefChoice(selectedIndex);
                //save the new pref
            }
        }
        else {
            findPreference(key).setSummary(sharedPreferences.getString(key,  MoviePreferences.getDefaultPrefChoice()));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //unregister the listener
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //register listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
}
