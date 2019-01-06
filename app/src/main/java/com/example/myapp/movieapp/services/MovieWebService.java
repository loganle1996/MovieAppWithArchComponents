package com.example.myapp.movieapp.services;

import com.example.myapp.movieapp.dataOffline.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MovieWebService {
    String BASE_URL = "https://api.themoviedb.org/";
    String FEED = "3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
//String BASE_URL = "http://560057.youcanlearnit.net/";
//    String FEED = "services/json/itemsfeed.php";
    //get the retrofit object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET(FEED)
    Call<List<Movie>> getAllMovies();


}
