/**
 * Created by Tiwuya on 22,August,2018
 */
/***************************************************************************************
 *    Referenced code samples from : http://square.github.io/retrofit/
 ***************************************************************************************/
package com.dappslocker.popularmovies.utilities;

import android.support.annotation.NonNull;

import com.dappslocker.popularmovies.model.MovieList;
import com.dappslocker.popularmovies.model.TrailerList;
import com.dappslocker.popularmovies.model.UserReview;
import com.dappslocker.popularmovies.model.UserReviewList;

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

    @GET("{movie_id}/videos")
    Call<TrailerList> getTrailers(@Path("movie_id") String movie_id,@Query("api_key") String apiKey);

    @GET("{movie_id}/reviews")
    Call<UserReviewList> getReviews(@Path("movie_id") String movie_id, @Query("api_key") String apiKey);

}
