package com.dev.moviedb.mvvm.moviesTab

import com.dev.moviedb.mvvm.repository.PopularMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.Observable

/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0.
 */
class MoviesTabViewModel constructor(private var popularMovieRepository: PopularMovieRepository){

    /**
     * Ask for the most popular movies
     */
    fun findMostPopularMovieList(): Observable<MovieCollectionDTO> {
        return popularMovieRepository.findAll()
    }


}