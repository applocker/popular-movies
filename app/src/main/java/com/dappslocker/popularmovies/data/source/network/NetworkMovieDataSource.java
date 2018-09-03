package com.dappslocker.popularmovies.data.source.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dappslocker.popularmovies.apikey.KeyUtil;
import com.dappslocker.popularmovies.data.source.MoviesDataSource;
import com.dappslocker.popularmovies.data.source.MoviesRepository;
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
 */
public class NetworkMovieDataSource implements MoviesDataSource {

    private static volatile NetworkMovieDataSource networkMovieDataSource;
    private static final String TAG = NetworkMovieDataSource.class.getSimpleName();
    private static MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private static String userPref = "";

    private NetworkMovieDataSource(String userPref) {
        NetworkMovieDataSource.userPref = userPref;
    }

    public static NetworkMovieDataSource getInstance(String userPref) {
        if (networkMovieDataSource == null) {
            synchronized (NetworkMovieDataSource.class) {
                if (networkMovieDataSource == null) {
                    networkMovieDataSource = new NetworkMovieDataSource(userPref);
                }
                else{
                    setUserPref(userPref);
                }
            }
        }
        return networkMovieDataSource;
    }

    private static void setUserPref(String userPref) {
        NetworkMovieDataSource.userPref = userPref;
    }

    @Override
    public LiveData<List<Movie>> getMovies() {
        loadFromNetwork();
        return movies;
    }

    @Override
    public void refreshData(@NonNull String userPref) {
        if(!userPref.equals(MoviesRepository.USER_PREF_FAVOURITE))
        NetworkMovieDataSource.userPref = userPref;
        loadFromNetwork();
    }

    private void loadFromNetwork() {
        GetMovieDataService service = RetrofitClient.getRetrofitInstance().create(GetMovieDataService.class);
        Call<MovieList> call =
                service.getMovies(userPref, KeyUtil.getApiKey());
        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
                if(response.isSuccessful()){
                    ResponseData(response.body());
                }
                else {
                    ResponseData(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
                Log.d(TAG, "Error retrieving movies from the Network");
                ResponseData(null);

            }
            private void ResponseData(MovieList moviesList) {
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

}
