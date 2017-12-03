package com.dev.moviedb.mvvm.moviesTab

import com.dev.moviedb.mvvm.repository.NowPlayingMovieRepository
import com.dev.moviedb.mvvm.repository.PopularMovieRepository
import com.dev.moviedb.mvvm.repository.TopRatedMovieRepository
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import io.reactivex.Observable
import io.reactivex.Single

/**
 * The ViewModel for the tab related with movie information
 *
 * Yamda 1.0.0.
 */
class MoviesTabViewModel constructor(private var apiService: TmdbApiService,
        private var popularMovieRepository: PopularMovieRepository,
        private var topRatedMovieRepository: TopRatedMovieRepository,
        private var nowPlayingMovieRepository: NowPlayingMovieRepository){


    fun getMostRecentMovie(): Single<MovieDTO> {
        return apiService.findLatestMovie()
    }


    /**
     * Ask for the most popular movies
     */
    fun findMostPopularMovieList(): Observable<MovieCollectionDTO> {
        return popularMovieRepository.findAll()
    }

    fun findTopRatedMoviesList(): Observable<MovieCollectionDTO> {
        return topRatedMovieRepository.findAll()
    }

    fun findNowPlayingMoviesList(): Observable<MovieCollectionDTO> {
        return nowPlayingMovieRepository.findAll()
    }


}