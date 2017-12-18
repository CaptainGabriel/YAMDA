package com.dev.moviedb.mvvm.moviesTab

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
        private var topRatedMovieRepository: TopRatedMovieRepository){


    fun getMostRecentMovie(): Single<MovieDTO> {
        return apiService.findLatestMovie()
    }


    /**
     * Ask for the most popular imageList
     */
    fun findMostPopularMovieList(): Observable<MovieCollectionDTO> {
        return popularMovieRepository.findAll()
    }


    fun findTopRatedMoviesList(): Observable<MovieCollectionDTO> {
        return topRatedMovieRepository.findAll()
    }

    fun findMovieById(id: Long, appendToResp: String = "") : Single<MovieDTO>{
        //TODO change to use repository later
        return apiService.findMovieById(id, appendToResp)
    }

}