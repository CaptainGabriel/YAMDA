package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.VideoDetails
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 * Yamda 1.0.0.
 */
class NowPlayingMovieRepository(private val api: TmdbApiService){


    fun findAll(): Observable<MovieCollectionDTO> {
        return api.findNowPlayingMovies()
    }

    fun findVideoDetailsForId(id: Int): Single<VideoDetails> {
        return api.findVideoDetailsForId(id)
    }

}