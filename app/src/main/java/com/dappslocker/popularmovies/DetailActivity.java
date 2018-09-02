package com.dappslocker.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.dappslocker.popularmovies.apikey.KeyUtil;
import com.dappslocker.popularmovies.database.FavouriteMoviesDao;
import com.dappslocker.popularmovies.database.MoviesDatabase;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.model.Trailer;
import com.dappslocker.popularmovies.model.TrailerList;
import com.dappslocker.popularmovies.model.UserReview;
import com.dappslocker.popularmovies.model.UserReviewList;
import com.dappslocker.popularmovies.utilities.GetMovieDataService;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.dappslocker.popularmovies.utilities.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    private static final String POSITION_CLICKED = "position_clicked";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_title)  TextView mTextViewTitle;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_rating)  TextView mTextViewRating;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_release_date)  TextView mTextViewReleaseDate;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_overview)  TextView mTextViewOverview;
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.imageView_movie_poster)   ImageView mImageViewMoviePoster;
    @BindView(R.id.toggleButton_favourite)   ToggleButton mToggleButtonFavourite;
    @BindView(R.id.recyclerView_trailer_videos)   RecyclerView mRecylerViewTrailers;
    @BindView(R.id.recyclerView_user_reviews)   RecyclerView mRecylerViewUserReview;

    private MoviesDatabase mMoviesDatabase;
    private static final String TAG = DetailActivity.class.getSimpleName();
    private int mSelectedMoviePosition;
    private Movie mCurrentSelectedMovie;
    private List<Movie> mfavouriteMovies = null;
    private static final int MOVIE_POSITION_FOR_QUERY_OPERATION = -1;
    private TrailerAdapter mTrailerAdapter;
    private UserReviewAdapter mUserReviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_detail_activity);
        ButterKnife.bind(this);
        //create the database instance
        mMoviesDatabase = MoviesDatabase.getInstance(this);
        Intent intent = getIntent();
        if(intent.hasExtra(POSITION_CLICKED)){
            mSelectedMoviePosition = intent.getIntExtra(POSITION_CLICKED,0);
            displayDataForItemAtPosition(mSelectedMoviePosition);
            setFavouriteMovieToggleButtonState();
        }
        //set up the trailer adapter and recyclyer view
        mTrailerAdapter = new TrailerAdapter(new ArrayList<Trailer>(),this);
        mRecylerViewTrailers.setAdapter(mTrailerAdapter);
        LinearLayoutManager layoutManagerTrailers = new LinearLayoutManager(this);
        mRecylerViewTrailers.setLayoutManager(layoutManagerTrailers);
        //set up the user review adapter and recyclyer view
        mUserReviewAdapter = new  UserReviewAdapter(new ArrayList<UserReview>());
        LinearLayoutManager layoutManagerUserReview = new LinearLayoutManager(this);
        mRecylerViewUserReview.setAdapter(mUserReviewAdapter);
        mRecylerViewUserReview.setLayoutManager(layoutManagerUserReview);
        loadMovieTrailers();
        loadMovieReviews();
        //load test data
            //loadTestDataTrailers();
            //loadTestDataUserReviews();

    }

    private void loadMovieTrailers() {
        GetMovieDataService service = RetrofitClient.getRetrofitInstance().create(GetMovieDataService.class);
        Call<TrailerList> call =
                service.getTrailers(Integer.valueOf(mCurrentSelectedMovie.getMovieID()).toString(),
                        KeyUtil.getApiKey());
        call.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(@NonNull Call<TrailerList> call, @NonNull Response<TrailerList> response) {
                if(response.isSuccessful()){
                    displayTrailerListResponseData(response.body());
                }
                else{
                    displayTrailerListResponseData(null);
                }
            }
            @Override
            public void onFailure( @NonNull Call<TrailerList> call,@NonNull Throwable t) {
                displayTrailerListResponseData(null);
                Toast.makeText(DetailActivity.this,
                        "Error loading movies...Please try later!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadMovieReviews() {
        GetMovieDataService service = RetrofitClient.getRetrofitInstance().create(GetMovieDataService.class);
        Call<UserReviewList> call =
                service.getReviews(Integer.valueOf(mCurrentSelectedMovie.getMovieID()).toString(),
                        KeyUtil.getApiKey());
        call.enqueue(new Callback<UserReviewList>() {
            @Override
            public void onResponse(@NonNull Call<UserReviewList> call, @NonNull Response<UserReviewList> response) {
                if(response.isSuccessful()){
                    displayUserReviewListResponseData(response.body());
                }
                else{
                    displayUserReviewListResponseData(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<UserReviewList> call, @NonNull Throwable t) {
                displayUserReviewListResponseData(null);
                Toast.makeText(DetailActivity.this,
                        "Error loading movies...Please try later!",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void displayTrailerListResponseData(TrailerList trailerList) {
        if (trailerList != null) {
            mTrailerAdapter.setTrailers(trailerList.getVideos());
        } else {
            Toast.makeText(DetailActivity.this,
                    "Error loading videos...Please try later!",
                    Toast.LENGTH_SHORT).show();        }
    }

    private void displayUserReviewListResponseData(UserReviewList userReviewList) {
        if (userReviewList != null) {
            mUserReviewAdapter.setUserReviews(userReviewList.getUserReviews());
        } else {
            Toast.makeText(DetailActivity.this,
                    "Error loading videos...Please try later!",
                    Toast.LENGTH_SHORT).show();        }
    }

    private void loadTestDataTrailers() {
        ArrayList<Trailer> trailers = new ArrayList<>();
        Trailer trailer1 =  new Trailer();
        Trailer trailer2 =  new Trailer();
        trailer1.setId("5a200baa925141033608f5f0");
        trailer1.setName("Official Trailer");
        trailer2.setId("925141033608f5f05a200baa");
        trailer2.setName("Official Trailer #2");
        trailers.add(trailer1);
        trailers.add(trailer2);
        mTrailerAdapter.setTrailers(trailers);
    }

    private void loadTestDataUserReviews() {
        ArrayList<UserReview> userReviews = new ArrayList<>();
        UserReview user1 =  new UserReview();
        UserReview user2 =  new UserReview();
        user1.setAuthor("user #1");
        user1.setContent(getResources().getString(R.string.test_user_review));
        user2.setAuthor("user #2");
        user2.setContent(getResources().getString(R.string.test_user_review));
        userReviews.add(user1);
        userReviews.add(user2);
        mUserReviewAdapter.setUserReviews(userReviews);
    }

    private void setFavouriteMovieToggleButtonState() {
        mToggleButtonFavourite.setChecked(false);
        executeDbOps(mSelectedMoviePosition,DatabaseOperationType.QUERY_SINGLE);
    }

    private  void  loadAllFavouriteMovies() {
        executeDbOps(MOVIE_POSITION_FOR_QUERY_OPERATION,DatabaseOperationType.QUERY);
    }

    private void executeDbOps(int position, final DatabaseOperationType dbOperation) {
        final Movie movie = position == MOVIE_POSITION_FOR_QUERY_OPERATION ? null:ImageAdapter.getMovieAtPosition(position);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final FavouriteMoviesDao favouriteMoviesDao = mMoviesDatabase.favouriteMoviesDao();
                if (dbOperation == DatabaseOperationType.INSERT && movie!= null) {
                    final Movie sMovie = favouriteMoviesDao.loadMovieId(movie.getMovieID());
                    if(sMovie == null){
                        favouriteMoviesDao.insertMovie(movie);
                        Log.i(TAG," executeDbOps: insert movie to the database");
                    }
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.DELETE && movie!= null){
                   favouriteMoviesDao.deleteMovie(movie);
                    Log.i(TAG," executeDbOps: deleted movie-> " + movie.getTitle());
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.QUERY_SINGLE && movie!= null){
                    final Movie sMovie = favouriteMoviesDao.loadMovieId(movie.getMovieID());
                    if(sMovie != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mToggleButtonFavourite.setChecked(true);
                                Log.i(TAG," executeDbOps: loaded all movies ");
                            }
                        });
                    }
                    showAllFavouriteMovies(favouriteMoviesDao);
                }else if ( dbOperation == DatabaseOperationType.QUERY){
                    final List<Movie> movies = favouriteMoviesDao.loadAllMovies();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mfavouriteMovies = movies;
                            Log.i(TAG," executeDbOps: loaded all movies ");
                        }
                    });
                    showAllFavouriteMovies(favouriteMoviesDao);
                }
            }
            private void showAllFavouriteMovies(FavouriteMoviesDao favouriteMoviesDao) {
                final List<Movie> movies = favouriteMoviesDao.loadAllMovies();
                if(movies != null && movies.size() > 0 ){
                    for (Movie m: movies) {
                        Log.i(TAG," executeDbOps:showAllFavouriteMovies " + m.getMovieID()
                                + " " +  m.getTitle());
                    }
                }
            }

            });
    }

    private void saveFavouriteToDatabase(int position, final DatabaseOperationType dbOperation) {
        executeDbOps(position,dbOperation);
    }

    private void displayDataForItemAtPosition(int position) {
        mCurrentSelectedMovie= ImageAdapter.getMovieAtPosition(position);
        if(mCurrentSelectedMovie != null ){
            mTextViewTitle.setText(mCurrentSelectedMovie.getTitle());
            mTextViewRating.setText(String.format(getResources().getConfiguration().locale,"%f", mCurrentSelectedMovie.getRating()));
            mTextViewReleaseDate.setText(mCurrentSelectedMovie.getReleaseDate());
            mTextViewOverview.setText(mCurrentSelectedMovie.getOverview());
            loadImage(mCurrentSelectedMovie);
        }
    }

    private void loadImage(Movie movie) {
        //re-load the image with picasso here using the poster url the images should now be cached in memory
        String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movie.getPosterUrl();
        Picasso.with(getApplicationContext()).load(imgUrl)
                .placeholder(R.drawable.ic_image_black_48dp)
                .into(mImageViewMoviePoster);
    }

    public void favouriteMovieClicked(View view) {
        if (mToggleButtonFavourite.isChecked()) {
            saveFavouriteToDatabase(mSelectedMoviePosition, DatabaseOperationType.INSERT);
            Log.i(TAG," favouriteMovieClicked: isChecked() -> true ");
        } else {
            //Delete movies from the database
            executeDbOps(mSelectedMoviePosition,DatabaseOperationType.DELETE);
            Log.i(TAG," favouriteMovieClicked: isChecked() -> false ");
        }
    }

    @Override
    public void onClickTrailer(int position) {
        //position identifies the trailer we want to view
        Trailer trailer = TrailerAdapter.getTrailerAtPosition(position);
        if(trailer != null){
            Intent intent = new  Intent(Intent.ACTION_VIEW);
            intent.setPackage("com.google.android.youtube");
            intent.setData(NetworkUtils.getYouTubeVideoURI(trailer.getKey()));
            if (intent.resolveActivity(getPackageManager()) != null) {
                // No Activity found that can handle this intent.
                startActivity(intent);
            }else{
                Toast.makeText(DetailActivity.this,"You need to have Youtube app to view this trailer",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public enum DatabaseOperationType {
        INSERT,
        DELETE,
        QUERY,
        QUERY_SINGLE
    }

}
