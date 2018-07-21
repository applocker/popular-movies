package com.dappslocker.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String POSITION_CLICKED = "position_clicked";
    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.textView_movie_id)  TextView mTextViewMovieId;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_movie_detail_activity);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if(intent.hasExtra(POSITION_CLICKED)){
            int position = intent.getIntExtra(POSITION_CLICKED,0);
            displayDataForItemAtPosition(position);
        }
    }

    private void displayDataForItemAtPosition(int position) {
        Movie movie = ImageAdapter.getMovieAtPosition(position);
        if(movie != null ){
            mTextViewMovieId.setText( String.format(getResources().getConfiguration().locale,"%d", movie.getMovieID()));
            mTextViewTitle.setText(movie.getTitle());
            mTextViewRating.setText(String.format(getResources().getConfiguration().locale,"%f", movie.getRating()));
            mTextViewReleaseDate.setText(movie.getReleaseDate());
            mTextViewOverview.setText(movie.getOverview());
            loadImage(movie);
        }
    }

    private void loadImage(Movie movie) {
        //re-load the image with picasso here using the poster url the images should now be cached in memory
        String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movie.getPosterUrl();
        Picasso.with(getApplicationContext()).load(imgUrl)
                .placeholder(R.drawable.ic_image_black_48dp)
                .into(mImageViewMoviePoster);
    }

}
