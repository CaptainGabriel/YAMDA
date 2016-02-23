package com.example.ricardopeixe.moviedb.network.http.apiservice;

import com.example.ricardopeixe.moviedb.model.dto.MovieAggregatorDTO;
import com.example.ricardopeixe.moviedb.model.dto.MovieDTO;
import com.example.ricardopeixe.moviedb.network.http.provider.MovieApiProvider;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

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
