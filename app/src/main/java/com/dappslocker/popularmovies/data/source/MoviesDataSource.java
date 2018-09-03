package com.dappslocker.popularmovies.data.source;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dappslocker.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Tiwuya on 02,September,2018
 */
//
public interface MoviesDataSource {
    LiveData<List<Movie>> getMovies();
    void refreshData(@NonNull String userPref);
}
