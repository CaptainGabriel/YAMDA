package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import com.dev.moviedb.mvvm.repository.remote.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class TvShowRepository(private val api: TmdbApiService) {


    fun findPopularTvSeries(): Observable<MovieCollectionDTO> = api.findPopularTvSeries()

    fun findTopRatedTvSeries(): Observable<MovieCollectionDTO> = api.findTopRatedTvSeries()

    fun findAiringTodayTvSeries(): Observable<MovieCollectionDTO> = api.findAiringTodayTvSeries()

    fun findLatestTvShow(): Observable<MovieDTO> = api.findLatestTvShow()

    fun findTvShowById(id: Long, appendToResponse: String) = api.findTvShowById(id, appendToResponse)

}