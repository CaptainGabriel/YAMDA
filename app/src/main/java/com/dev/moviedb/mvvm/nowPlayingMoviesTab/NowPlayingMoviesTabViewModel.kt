package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.VideoDetails
import io.reactivex.Observable
import io.reactivex.Single

/**
 *
 * Yamda 1.0.0.
 */
class NowPlayingMoviesTabViewModel
    constructor(private var nowPlayingMovieRepository: NowPlayingMovieRepository){




    fun findNowPlayingMovies(): Observable<MovieCollectionDTO> {
        return nowPlayingMovieRepository.findAll()
    }

    fun findVideoDetailsForId(id: Int): Single<VideoDetails> {
        return nowPlayingMovieRepository.findVideoDetailsForId(id)
    }


}