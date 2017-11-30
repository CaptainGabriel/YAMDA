package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.local.db.dao.NowPlayingDao
import com.dev.moviedb.mvvm.repository.local.db.entity.NowPlayingMovieWrapper
import com.dev.moviedb.mvvm.repository.mapper.MovieMapper
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable
import org.mapstruct.factory.Mappers

/**
 * Repository class for the NowPlayingMovie table.
 * Yamda 1.0.0.
 */
class NowPlayingMovieRepository(val api: TmdbApiService, val dao: NowPlayingDao): IRepository<NowPlayingMovieWrapper>{

    private val converter = Mappers.getMapper(MovieMapper::class.java)


    override fun findAll(): Observable<List<NowPlayingMovieWrapper>> {
        throw UnsupportedOperationException()
    }

    override fun findById(id: Long): Observable<NowPlayingMovieWrapper> {
        throw UnsupportedOperationException()
    }




}