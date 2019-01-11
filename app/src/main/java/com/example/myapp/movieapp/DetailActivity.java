package com.example.myapp.movieapp;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.utils.LoadYoutubeVideoTask;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {
    private TextView tvTitle, tvOverView;
    private RatingBar ratingBar;
    private Movie movie;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverView());
        ratingBar.setRating((float) movie.getRating());

        new LoadYoutubeVideoTask(youTubePlayerView).displayYouTubeVideo(movie);
    }

    private void init() {
        tvTitle = findViewById(R.id.title_detail);
        tvOverView = findViewById(R.id.tv_overview_dt);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        movie = getIntent().getParcelableExtra(MoviesAdapter.MVBUNDLE);
    }


}
