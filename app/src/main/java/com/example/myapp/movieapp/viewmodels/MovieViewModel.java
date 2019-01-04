package com.example.myapp.movieapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.myapp.movieapp.datasource.Movie;
import com.example.myapp.movieapp.datasource.Repository;

import java.util.List;
/*MovieViewModel, which lives longer than an Activity, gives the user the interface to manage the data*/
public class MovieViewModel extends AndroidViewModel {
    private Repository movieRepository; //using movie repository to obtain data
    private LiveData<List<Movie>> movieList; //just a reference to the list of movies

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = Repository.getInstance(application);
        movieList = movieRepository.getMovies();
    }

    //getter for movieList
    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    //all methods to manage data


}
