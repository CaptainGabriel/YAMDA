package com.dev.moviedb.utils;

import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.storage.repo.db.DataRecord;

/**
 * Map model objects into database models and vice-versa.
 */
public class DatabaseMapper {

  /**
   * Converts the instance of Movie into a DataRecord instance
   * that can be used to work with databases.
   *
   * @param movie
   *    Instance of Movie to convert into the DataRecord type.
   * @return
   *    DataRecord instance.
   */
  public static DataRecord convertFromMovie(Movie movie) {
    DataRecord record = new DataRecord();
    record.setId(movie.getPrimaryFacts().getId());
    record.setOverview(movie.getPrimaryFacts().getOverview());
    record.setOriginalTitle(movie.getPrimaryFacts().getOriginalTitle());
    record.setReleaseDate(movie.getPrimaryFacts().getReleaseDate());

    record.setBackdropImagePath(movie.getMovieImages().getBackdropImagePath());
    record.setPosterImagePath(movie.getMovieImages().getPosterImagePath());

    record.setVoteAverage(movie.getPopularity().getVoteAverage());
    return record;
  }


}
