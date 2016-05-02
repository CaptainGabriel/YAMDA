package com.example.dev.moviedb.utils;

import com.example.dev.moviedb.model.Movie;

import java.util.Comparator;

/**
 * Static class that provides anonymous implementations of Comparator.
 *
 * @version 0.0.2
 */
public class CompareUtils {

    private CompareUtils(){}

    public static Comparator<Movie> compareByReleaseDate(){
        return new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getPrimaryFacts().getReleaseDate()
                        .compareTo(rhs.getPrimaryFacts().getReleaseDate());
            }
        };
    }

}
