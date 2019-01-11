package com.example.myapp.movieapp.dataOffline;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.myapp.movieapp.utils.ImageCacheManager;
import com.example.myapp.movieapp.utils.MovieSavingTask;
import com.example.myapp.movieapp.utils.NetworkHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

public class Repository {
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private MovieDao mMovieDao; //all transaction with the database will go through this object
    private LiveData<List<Movie>> mMovies;
    private static Repository movieRepository = null;
    private Executor executor;
    private Application application;

    //Create a private constructor because we are using singleton pattern
    private Repository(Application application) {
        //create a LOCAL database to obtain dao object
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        mMovieDao = movieDatabase.getMovieDao();
        mMovies = mMovieDao.getAllMovies(); // get all movies using Dao object
        executor = Executors.newSingleThreadExecutor();
        this.application = application;
    }

    public static Repository getInstance(Application application) {
        if (movieRepository == null) {
            movieRepository = new Repository(application);
        }
        return movieRepository;
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    private void convertJsonArrayToMovieList(JSONArray movieJsonArray, List<Movie> movies) throws JSONException {
        for (int i = 0; i < movieJsonArray.length(); i++) {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
    }


    public void refreshData() {
        if (NetworkHelper.hasNetworkAccess(application)) {
            getOnlineData();
        } else {
            Toast.makeText(application, "Couldn't refresh", Toast.LENGTH_SHORT).show();
        }
    }

    private void getOnlineData() {
        ArrayList<Movie> onlineData = new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_URL, new JsonHttpResponseHandler() {
            //define a call back method
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) { // choose this method because the data gets back is an object
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    convertJsonArrayToMovieList(movieJsonArray, onlineData);
                    //save data to local database,data should be automatically synced to the UI
                    downloadImagesAndInsertAllMovies(onlineData);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Logan", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.e("Logan", "onFailure: " + errorResponse.toString());
                Toast.makeText(application, "Cannot refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Room api doesn't allow any transaction with sql database on the main thread,
     * avoiding poor performance. Any other transactions should be implemented below here*/
    public void downloadImagesAndInsertAllMovies(ArrayList<Movie> movieList) {
        MovieSavingTask movieSavingTask = new MovieSavingTask(mMovieDao, application);
        movieSavingTask.execute(movieList);
    }
//
//    public void downloadAndSaveImage(Movie movie, Context context) {
//        final String[] imageLocation = {movie.getLocalImageLocation()};
//        executor.execute(() -> {
//            Bitmap bitmap = ImageCacheManager.downloadBitMapImage(String.format("https://image.tmdb.org/t/p/w342%s", movie.getPosterPath()));
//            imageLocation[0] = ImageCacheManager.saveImageToInternalStorage(bitmap, context, movie.getId());
//            movie.setLocalImageLocation(imageLocation[0]);
//            insertMovie(movie);
//        });
//    }
//
//    private void insertMovie(Movie movie) {
//        executor.execute(() -> {
//            mMovieDao.insertMovie(movie);
//        });
//    }
}
