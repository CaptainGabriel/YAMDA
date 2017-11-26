package com.dev.moviedb.utils;

import com.dev.moviedb.mvvm.model.movies.Movie;

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
