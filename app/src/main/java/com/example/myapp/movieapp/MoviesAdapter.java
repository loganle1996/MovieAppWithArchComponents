package com.example.myapp.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.movieapp.datasource.Movie;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private Context context;
    private List<Movie> movies;

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle, tvOverView;
        private ImageView ivPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverView = itemView.findViewById(R.id.tvOverView);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }


        void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverView.setText(movie.getOverView());
            tvOverView.setOnClickListener(v -> {

            });

            //display image into imageView here

        }

        @Override
        public void onClick(View v) {
            //navigate to another activity here

        }
    }
    public void updateMovies(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    //Create a constructor that takes in a context and list of movies
    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
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
