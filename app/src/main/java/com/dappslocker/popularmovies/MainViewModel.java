package com.dappslocker.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dappslocker.popularmovies.data.source.MoviesRepository;
import com.dappslocker.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Tiwuya on 02,September,2018
 /***************************************************************************************
 *     Referenced code samples from UDACITY Lesson09b-ToDo-List-AAC
 ***************************************************************************************/

class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private static MoviesRepository moviesRepository;
    private static boolean prefChanged = false;
    private LiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = MoviesRepository.getInstance(getApplication(),MoviePreferences.getPrefChoice(getApplication()));
        movies = moviesRepository.getMovieLiveData();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public void refreshData() {
        moviesRepository.refreshData(MoviePreferences.getPrefChoice(getApplication()));
        Log.d(TAG, "refreshData: refreshing data");
    }
    public static void setPrefChanged(boolean prefChanged) {
        MainViewModel.prefChanged = prefChanged;
        Log.d(TAG, "setPrefChanged: user preference changed");
    }

    /**
     * Method refreshes the UI only when the user changes preference
    */
    public  void refreshDataIfPrefChanged() {
        if( MainViewModel.prefChanged){
            Log.d(TAG, "refreshDataIfPrefChanged: refreshing data");
            moviesRepository.refreshData(MoviePreferences.getPrefChoice(getApplication()));
            MainViewModel.prefChanged = false;
        }

    }


}

