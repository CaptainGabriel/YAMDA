package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class NowPlayingMoviesTabViewModel
    constructor(private var nowPlayingMovieRepository: NowPlayingMovieRepository){




    fun findNowPlayingMovies(): Observable<MovieCollectionDTO> {
        return nowPlayingMovieRepository.findAll()
    }


}