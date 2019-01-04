package com.example.myapp.movieapp.datasource;


import android.arch.lifecycle.LiveData;
import android.content.Context;

import java.util.List;

public class Repository {
    private MovieDao mMovieDao; //all transaction with the database will go through this object
    private LiveData<List<Movie>> mMovies;
    private static Repository movieRepository = null;

    //Create a private constructor because we are using singleton pattern
    private Repository(Context context) {
        //create a LOCAL database to obtain dao object
        MovieDatabase movieDatabase = MovieDatabase.getInstance(context);
        mMovieDao = movieDatabase.getMovieDao();
        mMovies = mMovieDao.getAllMovies(); // get all movies using Dao object
    }

    public static Repository getInstance(Context context) {
        if (movieRepository == null) {
            movieRepository = new Repository(context);
        }
        return movieRepository;
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    /*Room api doesn't allow any transaction with sql database on the main thread,
     * avoiding poor performance. Any other transactions should be implemented below here*/


}
