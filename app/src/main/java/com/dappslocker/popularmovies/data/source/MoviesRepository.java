package com.dappslocker.popularmovies.data.source;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

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
    private static MoviesDataSource moviesDataSource;
    public static final String USER_PREF_FAVOURITE = "favourites";
    private static MoviesRepository moviesRepository;
    private static final String TAG = MoviesRepository.class.getSimpleName();
    private static  Application mApplication;
    private MoviesRepository(@NonNull Application application, @NonNull String userPref) {
        MoviesRepository.mApplication = application;
        assignDataSource(application, userPref);
    }

    public static MoviesRepository getInstance(@NonNull Application application, @NonNull String userPref) {
        if(moviesRepository == null) {
            synchronized (MoviesRepository.class) {
                if(moviesRepository == null) {
                    moviesRepository = new MoviesRepository(application,userPref);
                }
            }
        }else{
            assignDataSource(application, userPref);
        }
        return moviesRepository;
    }

    private static void assignDataSource(@NonNull Application application, @NonNull String userPref) {
        if(userPref.equals(USER_PREF_FAVOURITE)) {
            moviesDataSource = DatabaseMovieDatasource.getInstance(application);
            Log.d(TAG,"MoviesRepository: moviesDataSource = << DatabaseMovieDatasource >>");
        }else {
            moviesDataSource = NetworkMovieDataSource.getInstance(NetworkUtils.getEndpoint(userPref));
            Log.d(TAG,"MoviesRepository: moviesDataSource = << NetworkMovieDataSource >>");
        }
    }

    @NonNull
    public LiveData<List<Movie>> getMovieLiveData() {
        return moviesDataSource.getMovies();
    }

    public void refreshData(String prefChoice) {
        if(!prefChoice.equals(USER_PREF_FAVOURITE)) {
            prefChoice = NetworkUtils.getEndpoint(prefChoice);
            moviesDataSource = NetworkMovieDataSource.getInstance(NetworkUtils.getEndpoint(prefChoice));
        }
        else{
            moviesDataSource = DatabaseMovieDatasource.getInstance(mApplication);
        }
        moviesDataSource.refreshData(prefChoice);
    }

}
