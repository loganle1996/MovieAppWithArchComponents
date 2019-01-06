package com.example.myapp.movieapp.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.myapp.movieapp.dataOffline.Movie;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;

import retrofit2.Call;

public class MyService extends IntentService {
    public static final String TAG = "My_Service";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";

    public MyService() {
        super("My_Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Logan", "onHandleIntent: debugging");
        //Make the webservice request to obtain data from webservice
//        MovieWebService movieWebService =
//                MovieWebService.retrofit.create(MovieWebService.class);
//
////        Call<List<Movie>> call = movieWebService.getAllMovies();
//
//        Movie[] movies;
//
//        try {
//            movies = call.execute()  //synchronously the data is returned
//                    .body();  //Body method gets the data that came back from the execute method
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.e("Logan", "onHandleIntent: " + e.getMessage());
//            return; // If the code reaches this catch block, there is nothing to return
//        }

        /*return the data to the MainActivity*/

        //create a message to broadcast
        Intent intentMessage = new Intent(MY_SERVICE_MESSAGE);
//        intentMessage.putExtra(MY_SERVICE_PAYLOAD, movies);


        //using LocalBroadCastManager to broadcast the message
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        localBroadcastManager.sendBroadcast(intentMessage);
    }

    private Movie[] toArray(List<Movie> movieList) {
        Movie[] movies = new Movie[movieList.size()];
        for (int i = 0; i < movieList.size(); i++) {
            movies[i] = movieList.get(i);
        }
        return movies;
    }
}
