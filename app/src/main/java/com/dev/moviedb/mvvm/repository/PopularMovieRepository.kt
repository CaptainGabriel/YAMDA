package com.dev.moviedb.mvvm.repository

import com.dev.moviedb.mvvm.repository.local.db.dao.PopularDao
import com.dev.moviedb.mvvm.repository.local.db.entity.PopularMovieWrapper
import com.dev.moviedb.mvvm.repository.remote.TmdbApiService
import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
class PopularMovieRepository(val api: TmdbApiService, val dao: PopularDao): IRepository<PopularMovieWrapper> {


    override fun findAll(): Observable<List<PopularMovieWrapper>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Long): Observable<PopularMovieWrapper> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}