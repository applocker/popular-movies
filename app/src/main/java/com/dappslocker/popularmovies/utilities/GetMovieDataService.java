/**
 * Created by Tiwuya on 22,August,2018
 */
/***************************************************************************************
 *    Referenced code samples from : http://square.github.io/retrofit/
 ***************************************************************************************/
package com.dappslocker.popularmovies.utilities;

import com.dappslocker.popularmovies.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface define the end points to retreive data from the movie database
 */
public interface GetMovieDataService {
    @GET("{endPoint}")
    Call<MovieList> getMovies(@Path("endPoint") String endPoint, @Query("api_key") String apiKey);
}
