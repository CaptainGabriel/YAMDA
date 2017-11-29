package com.dev.moviedb.mvvm.seriesTab

import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(dataService: TmdbApiService){

    private val service = dataService


    fun findPopularTvSeries(): Observable<MovieCollectionDTO> = service.findPopularTvSeries()

    fun findTopRatedTvSeries() = service.findTopRatedTvSeries()

}