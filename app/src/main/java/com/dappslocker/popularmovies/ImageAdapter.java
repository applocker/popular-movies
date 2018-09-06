package com.dappslocker.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dappslocker.popularmovies.model.Movie;
import com.dappslocker.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.List;


class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterViewHolder> {

    private final static  String TAG = ImageAdapter.class.getSimpleName();
    private final Context mContext;
    private static List<Movie> movieList;
    private final ImageAdapterOnClickHandler mClickHandler;

    public interface ImageAdapterOnClickHandler {
        void onClick(int position);
    }
    ImageAdapter(Context c,List<Movie> movieList,ImageAdapterOnClickHandler clickHandler ) {
        mContext = c;
        ImageAdapter.movieList = movieList;
        mClickHandler = clickHandler;
    }
    @NonNull
    @Override
    public ImageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.grid_view_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ImageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapterViewHolder holder, int position) {
        holder.loadImage(position);
    }


    @Override
    public int getItemCount() {
        if (movieList == null)
            return 0;
        return movieList.size();
    }

    public void setMovieList(List<Movie> listOfMovies) {
        ImageAdapter.movieList = null;
        ImageAdapter.movieList = listOfMovies;
        notifyDataSetChanged();
    }

    public static Movie getMovieAtPosition(int position) {
        if (movieList == null){
            return null;
        }
        else
            return movieList.get(position);
    }

    public class ImageAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        ImageAdapterViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView_grid_item);
            view.setOnClickListener(this);
        }
        private void loadImage(int position) {
            Log.i(TAG, ": start loading image at position " + position);
            //load the image with picasso here using the poster url
            String imgUrl = NetworkUtils.getPopularMoviesImagesUrlBase() + movieList.get(position).getPosterUrl();
            Picasso.with(mContext).load(imgUrl)
                    .placeholder(R.drawable.ic_hourglass_empty_white_48dp)
                    .error(R.drawable.ic_image_black_48dp)
                    .into(imageView);
            Log.i(TAG, ": finish loading image at position " + position);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }
}
