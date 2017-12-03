package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class NowPlayingMovieRepository(private val api: TmdbApiService){


    fun findAll(): Observable<MovieCollectionDTO> {
        return api.findNowPlayingMovies()
    }

}