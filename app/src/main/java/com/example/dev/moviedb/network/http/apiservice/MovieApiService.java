package com.example.dev.moviedb.network.http.apiservice;

import com.example.dev.moviedb.model.dto.MovieAggregatorDTO;
import com.example.dev.moviedb.model.dto.MovieDTO;
import com.example.dev.moviedb.network.http.provider.MovieApiProvider;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * MovieDTO Database API Specification
 *
 * This interface is used by {@link MovieApiProvider}
 * in order to create an wrapper to this methods.
 *
 * @version 0.0.1
 */
public interface MovieApiService {

    @GET("/3/movie/popular")
    Call<MovieAggregatorDTO> findMostPopularMovies(@Query("api_key") String apiKey,
                                                   @Query("language") String lang);

    @GET("/3/movie/now_playing")
    Call<MovieAggregatorDTO> findNowPlayingMovies(@Query("api_key") String apiKey,
                                                  @Query("language") String lang);

    @GET("/3/movie/upcoming")
    Call<MovieAggregatorDTO> findUpcomingMovies(@Query("api_key") String apiKey,
                                                @Query("language") String lang,
                                                @Query("page") int page);

    @GET("/3/movie/{id}")
    Call<MovieDTO> findMovieById(@Path("id") long id,
                                 @Query("api_key") String apiKey,
                                 @Query("language") String lang,
                                 @Query("append_to_response") String whatToAppend);

    @GET("/3/search/movie")
    Call<MovieAggregatorDTO> searchMoviesByKeyword(@Query("api_key") String apiKey,
                                                   @Query("query") String title,
                                                   @Query("language") String lang,
                                                   @Query("page") int page);


}
