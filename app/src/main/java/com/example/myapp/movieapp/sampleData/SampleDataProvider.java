package com.example.myapp.movieapp.sampleData;

import com.example.myapp.movieapp.dataOffline.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleDataProvider {
    public static List<Movie> movieList;
    public static Map<Integer, Movie> movieMap;

    static {
        movieList = new ArrayList<>();
        movieMap = new HashMap<>();
        addMovie(new Movie(1,"/i2dF9UxOeb77CAJrOflj0RpqJRF.jpg","Aquaman1",
                "Arthur Curry learns that he is the heir to the underwater kingdom of Atlantis, and must step forward to lead his people and be a hero to the world.",5.0));

        addMovie(new Movie(2,"/i2dF9UxOeb77CAJrOflj0RpqJRF.jpg","Aquaman2",
                "Arthur Curry learns that he is the heir to the underwater kingdom of Atlantis, and must step forward to lead his people and be a hero to the world.",5.0));

        addMovie(new Movie(3,"/i2dF9UxOeb77CAJrOflj0RpqJRF.jpg","Aquaman3",
                "Arthur Curry learns that he is the heir to the underwater kingdom of Atlantis, and must step forward to lead his people and be a hero to the world.",5.0));
    }

    private static void addMovie(Movie movie) {
        movieList.add(movie);
        movieMap.put(movie.getId(), movie);
    }
}
