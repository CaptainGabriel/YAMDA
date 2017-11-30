package com.dev.moviedb.mvvm.repository

import io.reactivex.Observable

/**
 *
 * Yamda 1.0.0.
 */
interface IRepository<T> {


    fun findAll(): Observable<List<T>>

    fun findById(id: Long): Observable<T>


}