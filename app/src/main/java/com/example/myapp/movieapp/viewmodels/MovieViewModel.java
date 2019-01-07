package com.example.myapp.movieapp.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.myapp.movieapp.dataOffline.Movie;
import com.example.myapp.movieapp.dataOffline.Repository;

import java.util.List;

import static com.loopj.android.http.AsyncHttpClient.log;

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

    public void refreshData(Context context) {
        movieRepository. refreshData(context);
    }

    //all methods to manage data
//    public void downloadAndSaveImage(Movie movie, Context context) {
//        movieRepository.downloadAndSaveImage(movie, context);
//    }

}
