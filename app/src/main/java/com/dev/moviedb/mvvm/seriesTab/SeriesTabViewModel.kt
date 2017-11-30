package com.dev.moviedb.mvvm.seriesTab

import com.dev.moviedb.mvvm.repository.local.db.entity.content.MovieCollection
import com.dev.moviedb.mvvm.repository.mapper.MovieMapper
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable
import org.mapstruct.factory.Mappers

/**
 *
 * Yamda 1.0.0.
 */
class SeriesTabViewModel constructor(dataService: TmdbApiService){

    private val service = dataService

    private val converse = Mappers.getMapper(MovieMapper::class.java)

    fun findPopularTvSeries(): Observable<MovieCollection> = service.findPopularTvSeries().map { t ->  converse.toMovieCollection(t)}

    fun findTopRatedTvSeries(): Observable<MovieCollection> = service.findTopRatedTvSeries().map { t ->  converse.toMovieCollection(t)}

}