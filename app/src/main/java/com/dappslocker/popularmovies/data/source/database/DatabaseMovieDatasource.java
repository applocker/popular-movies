package com.dappslocker.popularmovies.data.source.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.dappslocker.popularmovies.AppExecutors;
import com.dappslocker.popularmovies.data.source.MoviesDataSource;
import com.dappslocker.popularmovies.data.source.MoviesRepository;
import com.dappslocker.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Tiwuya on 02,September,2018
 */
public class DatabaseMovieDatasource implements MoviesDataSource {

    private static volatile DatabaseMovieDatasource databaseMovieDatasource;
    private static FavouriteMoviesDao favouriteMoviesDao;
    private static AppExecutors mAppExecutors;
    private static MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    private DatabaseMovieDatasource(Application application) {
        mAppExecutors = AppExecutors.getInstance();
        favouriteMoviesDao = MoviesDatabase.getInstance(application).favouriteMoviesDao();
    }

    public static DatabaseMovieDatasource getInstance(Application application) {
        if (databaseMovieDatasource == null) {
            synchronized (DatabaseMovieDatasource.class) {
                if (databaseMovieDatasource == null) {
                    databaseMovieDatasource = new DatabaseMovieDatasource(application);
                }
            }
        }
        return databaseMovieDatasource;
    }

    @Override
    public LiveData<List<Movie>> getMovies() {
        mAppExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
           List<Movie> favMovies = favouriteMoviesDao.loadAllMovies();
                if( favMovies != null &&  favMovies.size()> 0){
                    movies.postValue(favMovies);
                }else{
                    movies.postValue(null);
                }
            }
        });
        return movies;
    }

    @Override
    public void refreshData(@NonNull String userPref) {
        if(userPref.equals(MoviesRepository.USER_PREF_FAVOURITE)){
            getMovies();
        }
    }



}
