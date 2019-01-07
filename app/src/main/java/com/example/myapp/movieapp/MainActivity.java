package com.example.myapp.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.utils.NetworkHelper;
import com.example.myapp.movieapp.viewmodels.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

/*
 * 1. The app shows offline data while:
 *    a. Fetching for new data
 *    b. If successful ->
 *       1. save it to local database a
 *       2. the system will automatically update the UI (No action should be required)
 *    c. Otherwise, send an error message to the user*/

public class MainActivity extends AppCompatActivity {
    private List<Movie> movies;
    private MoviesAdapter moviesAdapter;
    private MovieViewModel movieViewModel;

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
        //Attempting to refresh data when first opening the app
        refreshDataOnline(this);
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe_id);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            refreshDataOnline(this);
            new Handler().postDelayed(() -> {
                swipeRefreshLayout.setRefreshing(false);
            }, 1000);
        });
    }

    //This method should also be defined in the repository
    private void refreshDataOnline(Context context) {
        if (NetworkHelper.hasNetworkAccess(this)) {
            movieViewModel.refreshData(context);
        } else {
            //show all offline local movies here
            Toast.makeText(this, "No wifi connection", Toast.LENGTH_LONG).show();
        }

    }

    private void subcribeData() {
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
        moviesAdapter = new MoviesAdapter(this, movies, movieViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(moviesAdapter); //pair recyclerView and an adapter together
    }

    private void init() {
        //define MovieViewModel
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movies = new ArrayList<>();
    }
}
