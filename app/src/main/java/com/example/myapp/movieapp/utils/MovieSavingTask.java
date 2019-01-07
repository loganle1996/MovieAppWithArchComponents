package com.example.myapp.movieapp.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.mtp.MtpObjectInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

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
        for (Movie movie : arrayList) {
            String imageUrl = String.format("https://image.tmdb.org/t/p/w342%s", movie.getPosterPath());
            Bitmap bitmap = ImageCacheManager.downloadBitMapImage(imageUrl);
            String imageLocalLocation = ImageCacheManager.saveImageToInternalStorage(bitmap, application, movie.getId());
            movie.setLocalImageLocation(imageLocalLocation);
            //update movie in the database
            movieDao.insertMovie(movie);
        }
        return null;
    }

}
