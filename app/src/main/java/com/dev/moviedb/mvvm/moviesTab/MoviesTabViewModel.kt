package com.dev.moviedb.mvvm.moviesTab

import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0.
 */
class MoviesTabViewModel constructor(dataService: TmdbApiService){


    private val service = dataService


    /**
     * Ask for the most popular movies
     */
    fun findMostPopularMovieList(): Observable<MovieCollectionDto> = service.findMostPopularMovies()

    /**
     * Ask for the most top rated movies
     */
    fun findTopRatedMoviesList(): Observable<MovieCollectionDto> = service.findTopRatedmovies()

    /**
     * Ask for the list of now playing movies
     */
    fun findNowPlayingMoviesList(): Observable<MovieCollectionDto> = service.findNowPlayingMovies()


}