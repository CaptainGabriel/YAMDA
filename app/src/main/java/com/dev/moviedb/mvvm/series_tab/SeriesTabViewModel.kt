package com.dev.moviedb.mvvm.series_tab

import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(dataService: TmdbApiService){

    private val service = dataService


    fun findPopularTvSeries(): Observable<MovieCollectionDto> = service.findPopularTvSeries()

    fun findTopRatedTvSeries() = service.findTopRatedTvSeries()

}