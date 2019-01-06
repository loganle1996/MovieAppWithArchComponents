package com.example.myapp.movieapp.dataOffline;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllMovies(List<Movie> movies);

    @Query("DELETE FROM movie_table WHERE movie_id = :id")
    int deleteMovieById(int id);

    @Query("DELETE FROM MOVIE_TABLE")
    void deleteAllNotes();

    @Query("SELECT * FROM movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movie_table WHERE movie_id = :id")
    LiveData<Movie> getMovieById(int id);
}
