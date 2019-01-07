package com.example.myapp.movieapp.utils;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.dataOffline.MovieDao;

import java.util.ArrayList;

public class MovieSavingTask extends AsyncTask<ArrayList<Movie>, Void, Void> {
    private MovieDao movieDao;
    private Application application;

    public MovieSavingTask(MovieDao movieDao, Application application) {
        this.movieDao = movieDao;
        this.application = application;
    }

    @Override
    protected Void doInBackground(ArrayList<Movie>... params) {
        ArrayList<Movie> arrayList = params[0];
        arrayList.stream().forEach(movie -> {
            String imageUrl = String.format("https://image.tmdb.org/t/p/w342%s", movie.getPosterPath());
            Bitmap bitmap = ImageCacheManager.downloadBitMapImage(imageUrl);
            String imageLocalLocation = ImageCacheManager.saveImageToInternalStorage(bitmap, application, movie.getId());
            movie.setLocalImageLocation(imageLocalLocation);
            //update movie in the database
            movieDao.insertMovie(movie);
        });
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
