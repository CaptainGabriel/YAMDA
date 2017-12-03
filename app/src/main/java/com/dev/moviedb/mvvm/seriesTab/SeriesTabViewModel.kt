package com.dev.moviedb.mvvm.seriesTab

import com.dev.moviedb.mvvm.repository.remote.TmdbApiService

/**
 *
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(dataService: TmdbApiService){

    private val service = dataService


    //fun findPopularTvSeries(): Observable<MovieCollection> = service.findPopularTvSeries().map { t ->  converse.toMovieCollection(t)}

    //fun findTopRatedTvSeries(): Observable<MovieCollection> = service.findTopRatedTvSeries().map { t ->  converse.toMovieCollection(t)}

}