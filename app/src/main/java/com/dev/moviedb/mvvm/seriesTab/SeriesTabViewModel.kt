package com.dev.moviedb.mvvm.seriesTab

import com.dev.moviedb.mvvm.repository.TvShowRepository
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import io.reactivex.Observable

/**
 * The ViewModel for the tab related with tv show's information
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(private var repo: TvShowRepository){

    /**
     * Ask for the most popular tv shows
     */
    fun findPopularTvSeries(): Observable<MovieCollectionDTO> = repo.findPopularTvSeries()

    /**
     * Ask for the top rated tv shows
     */
    fun findTopRatedTvSeries(): Observable<MovieCollectionDTO> = repo.findTopRatedTvSeries()

    /**
     * Ask for the tv shows airing today
     */
    fun findAiringTodayTvSeries(): Observable<MovieCollectionDTO> = repo.findAiringTodayTvSeries()

    fun getLatestTvShow(): Observable<MovieDTO> = repo.findLatestTvShow()

}