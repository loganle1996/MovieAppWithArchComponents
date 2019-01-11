package com.example.myapp.movieapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.myapp.movieapp.R;
import com.example.myapp.movieapp.dataOffline.Movie;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class LoadYoutubeVideoTask {
    private final String YOUTUBE_API_KEY = "AIzaSyDDrbsueEz0DWfF5z5A2L0s8Y3IKVkf9Mg";
    private YouTubePlayerView youTubePlayerView;
    private final String TRAILIER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public LoadYoutubeVideoTask(YouTubePlayerView youTubePlayerView) {
        this.youTubePlayerView = youTubePlayerView;
    }

    private void initiliazeYoutube(String youtubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(youtubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("SMILE", "onInitializationFailure: " + youTubeInitializationResult);
            }
        });
    }

    public void displayYouTubeVideo(Movie movie) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILIER_URL, movie.getId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() == 0) {
                        //should show some images instead over here
                        return;
                    }
                    JSONObject movieTrailer = results.getJSONObject(0);
                    String trailerType = movieTrailer.getString("site");
                    if (!trailerType.equalsIgnoreCase("YouTube")) {
                        //should show some images instead over here
                        return;
                    }
                    String youtubeKey = movieTrailer.getString("key");
                    initiliazeYoutube(youtubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
