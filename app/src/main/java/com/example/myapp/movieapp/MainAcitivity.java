package com.example.myapp.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.myapp.movieapp.datasource.Movie;
import com.example.myapp.movieapp.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainAcitivity extends AppCompatActivity {
    private List<Movie> movies;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acitivity);
        //get references to layouts
        init();

        //initialize recycler making sure it's ready to display.
        //At this point, it doesn't have any data in the recylcer view, displaying nothing
        initRecylerView();

        //subcribe recyclerView(adapter) to livedata
        //RecyclerView(adapter) gets new livedata (updatedDataList) and automatically updates its UI.
        //(how to update ? programmer must specify when defining an observer)
        subcribeData();

    }

    private void subcribeData() {
        //define MovieViewModel
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        //define action for whenever data changes in the database or webservices to the observer
        //adapter must update.
        Observer<List<Movie>> observer = updatedMovieList -> {
            moviesAdapter.updateMovies(updatedMovieList);
        };
        //Let the observer observe the list of movie
        movieViewModel.getMovieList().observe(this, observer);
    }

    private void initRecylerView() {
        RecyclerView recyclerView = findViewById(R.id.rvMovies);
        moviesAdapter = new MoviesAdapter(this, movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(moviesAdapter); //pair recyclerView and an adapter together
    }

    private void init() {
        movies = new ArrayList<>();
    }
}
