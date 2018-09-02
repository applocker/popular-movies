package com.dappslocker.popularmovies.data.source.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dappslocker.popularmovies.model.Movie;

import java.util.List;

/**
 * Created by Tiwuya on 23,August,2018
 *
 * This interface define the Dao for reading the favouriteMovies entries
 */
@Dao
public interface FavouriteMoviesDao {
    @Query("SELECT * FROM favouriteMovies ORDER BY movieId")
    List<Movie> loadAllMovies();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("SELECT * FROM favouriteMovies WHERE movieId = :movieId")
    Movie loadMovieId(int movieId);

    @Query("SELECT * FROM favouriteMovies ORDER BY movieId")
    LiveData<List<Movie>> loadAllFavouriteMovies();

}
