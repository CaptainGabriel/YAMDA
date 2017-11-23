package com.dev.moviedb.mvvm.moviesTab

import com.dev.moviedb.model.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class MoviesTabViewModel constructor(dataService: TmdbApiService){


    val service = dataService


    fun getMostPopularMovieList(): Observable<MovieCollectionDto> = service.findMostPopularMovies()

    fun TopRatedMoviesList(): Observable<MovieCollectionDto> = service.findTopRatedmovies()


}