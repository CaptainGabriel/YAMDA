package com.dev.moviedb.mvvm.seriesTab

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import io.reactivex.Observable

/**
 * The ViewModel for the tab related with tv show's information
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(private var dataService: TmdbApiService){

    /**
     * Ask for the most popular tv shows
     */
    fun findPopularTvSeries(): Observable<MovieCollectionDTO> = dataService.findPopularTvSeries()

    /**
     * Ask for the top rated tv shows
     */
    fun findTopRatedTvSeries(): Observable<MovieCollectionDTO> = dataService.findTopRatedTvSeries()

    /**
     * Ask for the tv shows airing today
     */
    fun findAiringTodayTvSeries(): Observable<MovieCollectionDTO> = dataService.findAiringTodayTvSeries()

}