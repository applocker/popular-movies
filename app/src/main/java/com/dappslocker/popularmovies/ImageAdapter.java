package com.dappslocker.popularmovies;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Tiwuya on 05/07/2018.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    public final static  String TAG = ImageAdapter.class.getSimpleName();
    private Context mContext;

    private static List<Movie> movieList;

    public ImageAdapter(Context c,List<Movie> movieList) {
        super(c,0,movieList);
        mContext = c;
        this.movieList = movieList;
    }

    public int getCount() {
        if (movieList == null)
            return 0;
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return movieList.indexOf( movieList.get(position));
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i(TAG, ": start loading image at postion " + position);
        final ImageView imageView;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_view_item, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.imageView_grid_item);
        }else  {
            imageView = (ImageView) convertView;
        }
        //load the image with picasso here using the poster url
        String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movieList.get(position).getPosterUrl();
        Picasso.with(mContext)
                .load(imgUrl)
                .placeholder(R.drawable.ic_image_black_48dp)
                .into(imageView,new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        movieList.get(position).setPosterImage(imageView.getDrawable());
                    }
                    @Override
                    public void onError() {
                        //set default image as the drawable for the movie object at this position instead
                        movieList.get(position).setPosterImage(mContext.getResources().getDrawable(R.drawable.ic_image_black_48dp));
                    }
                });
        Log.i(TAG, ": finish loading image at postion " + position);
        return imageView;
    }


    public void setMovieList(List<Movie> movieList) {
        this.movieList = null;
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public static Movie getMovieAtPosition(int position) {
        if (movieList == null){
            return null;
        }
        else
            return movieList.get(position);
    }
}
