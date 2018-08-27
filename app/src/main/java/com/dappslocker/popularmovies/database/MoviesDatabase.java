package com.dappslocker.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.dappslocker.popularmovies.model.Movie;

/**
 * Created by Tiwuya on 23,August,2018
 /***************************************************************************************
 *    Referenced code samples from UDACITY Lesson09b-ToDo-List-AAC
 ***************************************************************************************/
@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MoviesDatabase extends RoomDatabase {
    private static final String LOG_TAG = MoviesDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "Movies";
    private static MoviesDatabase sInstance;

    public static MoviesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDatabase.class, MoviesDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract FavouriteMoviesDao favouriteMoviesDao();

}
