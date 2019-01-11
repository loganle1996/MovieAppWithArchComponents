package com.example.myapp.movieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.utils.ImageCacheManager;
import com.example.myapp.movieapp.viewmodels.MovieViewModel;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;
    private MovieViewModel movieViewModel;
    public static final String MVBUNDLE = "MVBUNDLE";

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvOverView;
        private ImageView ivPoster;
        private View container;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverView = itemView.findViewById(R.id.tvOverView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }


        void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverView());
            try {

                ivPoster.setImageBitmap(ImageCacheManager.
                        getBitMapFromStorage(movie.getLocalImageLocation()));
            } catch (FileNotFoundException e) {
                Log.e("Logan", "bind: " + e.getMessage());
            }
            container.setOnClickListener(v -> {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(MVBUNDLE, movie);
                context.startActivity(intent);
            });
        }

    }

    public void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    //Create a constructor that takes in a context and list of movies
    public MoviesAdapter(Context context, List<Movie> movies, MovieViewModel movieViewModel) {
        this.context = context;
        this.movies = movies;
        this.movieViewModel = movieViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mov, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Movie movie = movies.get(position);
        viewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        } else {
            return 0;
        }
    }
}
