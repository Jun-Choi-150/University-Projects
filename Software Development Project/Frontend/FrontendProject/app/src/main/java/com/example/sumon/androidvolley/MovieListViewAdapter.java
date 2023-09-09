package com.example.sumon.androidvolley;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.sumon.androidvolley.app.AppController;

import java.util.ArrayList;
import java.util.List;
/**
 * @author sabrinaf
 */
public class MovieListViewAdapter extends RecyclerView.Adapter<MovieListViewAdapter.ViewHolder> {

//    private List<Integer> mViewColors;
    private List<MovieNames> mMovies;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MovieListViewAdapter(Context context, List<MovieNames> movies) {
        this.mInflater = LayoutInflater.from(context);
        this.mMovies = movies;
    }

    // inflates the row layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie_list_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the view and textview in each row
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        String movie = mMovies.get(position).getMovieName();
        holder.myImageView.setImageUrl(movie, imageLoader);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        View myView;
        NetworkImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
//            myView = itemView.findViewById(R.id.colorView);
            myImageView = itemView.findViewById(R.id.movieView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public MovieNames getItem(int id) {
        return mMovies.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}