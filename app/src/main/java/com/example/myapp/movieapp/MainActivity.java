package com.example.myapp.movieapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.sampleData.SampleDataProvider;
import com.example.myapp.movieapp.services.MyService;
import com.example.myapp.movieapp.utils.NetworkHelper;
import com.example.myapp.movieapp.viewmodels.MovieViewModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.loopj.android.http.AsyncHttpClient.log;

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
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            assert intent != null;
            Movie[] data = (Movie[]) intent
                    .getParcelableArrayExtra(MyService.MY_SERVICE_PAYLOAD);

            Toast.makeText(MainActivity.this,
                    "Received " + data.length + " items from service",
                    Toast.LENGTH_SHORT).show();

            movies = Arrays.asList(data);//temporary
            moviesAdapter.updateMovies(movies);

        }
    };

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
        //temporary should be placed in repository
//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .registerReceiver(mBroadcastReceiver,
//                        new IntentFilter(MyService.MY_SERVICE_MESSAGE));
        refreshDataOnline(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        LocalBroadcastManager.getInstance(getApplicationContext())
//                .unregisterReceiver(mBroadcastReceiver);
    }

    //This method should also be defined in the repository
    private void refreshDataOnline(Context context) {

        if (NetworkHelper.hasNetworkAccess(this)) {
            Button refreshBtn = findViewById(R.id.btn_refresh);
            refreshBtn.setOnClickListener(v -> {
                movieViewModel.refreshData(context);
            });


        } else {
            //show all offline local movies here
            Toast.makeText(this, "No wifi connection", Toast.LENGTH_LONG).show();
        }
//        Intent intent = new Intent(this, MyService.class);
//        startService(intent); // trigger intentService that I defined

    }

    private void subcribeData() {
        //define MovieViewModel
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
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
