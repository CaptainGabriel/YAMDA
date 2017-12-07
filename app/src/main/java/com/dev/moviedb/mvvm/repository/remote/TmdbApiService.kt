package com.dev.moviedb.mvvm.repository.remote

import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import com.dev.moviedb.mvvm.repository.remote.dto.VideoDetails
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * This protocol defines the operations over against the web api.
 */
interface TmdbApiService {


    @GET("/3/movie/popular")
    fun findMostPopularMovies(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                              @Query("language") lang: String = "en"): Observable<MovieCollectionDTO>

    @GET("/3/movie/now_playing")
    fun findNowPlayingMovies(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                             @Query("language") lang: String = "en"): Observable<MovieCollectionDTO>

    @GET("/3/movie/{movie_id}/videos")
    fun findVideoDetailsForId(@Path("movie_id") movieId: Int,
                              @Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                              @Query("language") lang: String = "en"): Single<VideoDetails>


    @GET("/3/movie/top_rated")
    fun findTopRatedmovies(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                           @Query("language") lang: String = "en"): Observable<MovieCollectionDTO>

    @GET("/3/movie/latest")
    fun findLatestMovie(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                           @Query("language") lang: String = "en"): Single<MovieDTO>

    @GET("/3/movie/upcoming")
    fun findUpcomingMovies(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                           @Query("language") lang: String = "en",
                           @Query("page") page: Int = 1): Observable<MovieCollectionDTO>

    @GET("/3/movie/{id}")
    fun findMovieById(@Path("id") id: Long,
                      @Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                      @Query("language") lang: String = "en",
                      @Query("append_to_response") whatToAppend: String): Call<MovieDTO>

    @GET("/3/search/movie")
    fun searchMoviesByKeyword(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                              @Query("query") title: String,
                              @Query("language") lang: String = "en",
                              @Query("page") page: Int): Call<MovieCollectionDTO>


    @GET("/3/tv/popular")
    fun findPopularTvSeries(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                            @Query("language") lang: String = "en"):  Observable<MovieCollectionDTO>


    @GET("/3/tv/top_rated")
    fun findTopRatedTvSeries(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                             @Query("language") lang: String = "en"):  Observable<MovieCollectionDTO>

    @GET("/3/tv/airing_today")
    fun findAiringTodayTvSeries(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                                @Query("language") lang: String = "en"):  Observable<MovieCollectionDTO>

    @GET("/3/tv/latest")
    fun findLatestTvShow(@Query("api_key") apiKey: String = RemoteCom.API_KEY_DEV,
                                @Query("language") lang: String = "en"):  Observable<MovieDTO>
}
