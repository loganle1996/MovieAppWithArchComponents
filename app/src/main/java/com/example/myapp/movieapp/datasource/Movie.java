package com.example.myapp.movieapp.datasource;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;


@Entity(tableName = "movie_table")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private int id;
    @ColumnInfo(name = "image_poster_path")
    private String posterPath;
    @ColumnInfo(name = "movie_title")
    private String title;
    @ColumnInfo(name = "movie_review")
    private String overView;
    @ColumnInfo(name = "movie_rating")
    private double rating;

    //A constructor to create a movie object when pulling data from the server
    @Ignore
    public Movie(@NonNull JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overView = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
    }

    public Movie(@NonNull int id, @NonNull String posterPath, @NonNull String title, @Nullable String overView, @Nullable double rating) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.overView = overView;
        this.rating = rating;
    }

    //getters
    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public double getRating() {
        return rating;
    }

}
