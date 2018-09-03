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
    private FavouriteMoviesDao favouriteMoviesDao;
    private AppExecutors mAppExecutors;
    private static MutableLiveData<List<Movie>> movies = new MutableLiveData<>();

    private DatabaseMovieDatasource(Application application) {
        //movies = new MutableLiveData<>();
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
                movies.postValue(favouriteMoviesDao.loadAllFavouriteMovies().getValue());
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
