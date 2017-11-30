package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.local.db.dao.TopRatedDao
import com.dev.moviedb.mvvm.repository.local.db.entity.TopRatedMovieWrapper
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class TopRatedRepository(val api: TmdbApiService, val dao: TopRatedDao): IRepository<TopRatedMovieWrapper>{


    override fun findAll(): Observable<List<TopRatedMovieWrapper>> {
        throw UnsupportedOperationException()
    }

    override fun findById(id: Long): Observable<TopRatedMovieWrapper> {
        throw UnsupportedOperationException()
    }

}