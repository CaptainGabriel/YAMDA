package com.dev.moviedb.mvvm.moviesTab

import com.dev.moviedb.mvvm.modelstate.movies.content.MovieCollection
import com.dev.moviedb.mvvm.modelstate.movies.mapper.MovieMapper
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable
import org.mapstruct.factory.Mappers

/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0.
 */
class MoviesTabViewModel constructor(dataService: TmdbApiService){


    private val service = dataService

    private val converter = Mappers.getMapper(MovieMapper::class.java)


    /**
     * Ask for the most popular movies
     */
    fun findMostPopularMovieList(): Observable<MovieCollection> = service.findMostPopularMovies().map { t -> converter.toMovieCollection(t) }

    /**
     * Ask for the most top rated movies
     */
    fun findTopRatedMoviesList(): Observable<MovieCollection> = service.findTopRatedmovies().map { t -> converter.toMovieCollection(t) }

    /**
     * Ask for the list of now playing movies
     */
    fun findNowPlayingMoviesList(): Observable<MovieCollection> = service.findNowPlayingMovies().map { t -> converter.toMovieCollection(t) }


}