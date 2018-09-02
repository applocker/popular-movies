package com.dappslocker.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dappslocker.popularmovies.apikey.KeyUtil;
import com.dappslocker.popularmovies.data.MoviePreferences;
import com.dappslocker.popularmovies.database.MoviesDatabase;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.model.MovieList;
import com.dappslocker.popularmovies.utilities.GetMovieDataService;
import com.dappslocker.popularmovies.utilities.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tiwuya on 02,September,2018
 /***************************************************************************************
 *     Referenced code samples from UDACITY Lesson09b-ToDo-List-AAC
 ***************************************************************************************/

class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();
    private static final String USER_PREF_FAVOURITE = "favourite";
    private static MutableLiveData<List<Movie>> movies;

    public MainViewModel(@NonNull Application application) {
        super(application);
        movies = new MutableLiveData<>();
        String userPref = MoviePreferences.getPrefChoice(getApplication());
        if(userPref.equals(USER_PREF_FAVOURITE)) {
            loadFromDatabase();
        }else {
            loadFromNetwork(userPref);
        }
    }

    private void loadFromNetwork(String userPref) {
        GetMovieDataService service = RetrofitClient.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MovieList> call =
                service.getMovies(userPref, KeyUtil.getApiKey());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call,@NonNull Response<MovieList> response) {
                if(response.isSuccessful()){
                    displayResponseData(response.body());
                }
                else {
                    displayResponseData(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.d(TAG, "Error retrieving movies from the Network");
                displayResponseData(null);

            }
            private void displayResponseData(MovieList moviesList) {
                if (moviesList != null) {
                   movies.postValue(moviesList.getMovies());
                    Log.d(TAG, "retrieving movie from the Network was sucessfull");
                } else {
                    movies.postValue(null);
                    Log.d(TAG, "retrieving movie from the Network was not sucessfull");
                }
            }

        });
    }


    private void loadFromDatabase() {
        MoviesDatabase database = MoviesDatabase.getInstance(this.getApplication());
        Log.d(TAG, "retrieving movie from the DataBase");
        movies = (MutableLiveData<List<Movie>>) database.favouriteMoviesDao().loadAllFavouriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}

