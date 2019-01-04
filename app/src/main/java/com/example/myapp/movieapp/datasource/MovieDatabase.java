package com.example.myapp.movieapp.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/*Creating a database is quite expensive -> using singleton pattern */
//define a database for movie class
@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "movie_database";
    private static MovieDatabase movieDatabase;

    //an abstract method that returns
    public abstract MovieDao getMovieDao();

    public static MovieDatabase getInstance(final Context context) {
        if (movieDatabase == null) {
            synchronized (MovieDatabase.class) {
                movieDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MovieDatabase.class, //
                        DATABASE_NAME).build();
            }
        }
        return movieDatabase;
    }

}
