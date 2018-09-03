package com.dappslocker.popularmovies.data.source;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.dappslocker.popularmovies.data.source.database.DatabaseMovieDatasource;
import com.dappslocker.popularmovies.data.source.network.NetworkMovieDataSource;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;

import java.util.List;

/**
 * Created by Tiwuya on 02,September,2018
 ***************************************************************************************
 *     Referenced code samples from https://github.com/googlesamples/android-architecture
 ***************************************************************************************/

public class MoviesRepository {
    private static MoviesDataSource moviesDataSource = null;
    public static final String USER_PREF_FAVOURITE = "favourite";
    private static MoviesRepository moviesRepository;

    private MoviesRepository(@NonNull Application application, @NonNull String userPref) {
        if(userPref.equals(USER_PREF_FAVOURITE)) {
            moviesDataSource = DatabaseMovieDatasource.getInstance(application);
        }else {
            moviesDataSource = NetworkMovieDataSource.getInstance(NetworkUtils.getEndpoint(userPref));
        }
    }

    public static MoviesRepository getInstance(@NonNull Application application, @NonNull String userPref) {
        if(moviesRepository == null) {
            synchronized (MoviesRepository.class) {
                if(moviesRepository == null) {
                    moviesRepository = new MoviesRepository(application,userPref);
                }
            }
        }
        return moviesRepository;
    }

    @NonNull
    public LiveData<List<Movie>> getMovieLiveData() {
        return moviesDataSource.getMovies();
    }

    public void refreshData(String prefChoice) {
        if(!prefChoice.equals(USER_PREF_FAVOURITE)) {
            prefChoice = NetworkUtils.getEndpoint(prefChoice);
        }
        moviesDataSource.refreshData(prefChoice);
    }
}
