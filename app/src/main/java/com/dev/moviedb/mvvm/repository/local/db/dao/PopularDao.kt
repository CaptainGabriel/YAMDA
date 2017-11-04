package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.model.Movie
import com.dev.moviedb.mvvm.repository.ITaskListener

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Dao
abstract class PopularDao : IDao<Movie>{


    @Query("SELECT * FROM popular_movie")
    abstract override fun insert(elem: Movie, onComplete: ITaskListener)

    @Query("SELECT * FROM popular_movie")
    abstract override fun insertAll(vararg elements: Movie, onComplete: ITaskListener)

    @Query("UPDATE FROM popular_movie WHERE id = :elem.identifier")
    abstract override fun update(elem: Movie, onComplete: ITaskListener)

    @Query("UPDATE FROM popular_movie")
    abstract override fun updateAll(vararg elements: Movie, onComplete: ITaskListener)

    @Query("DELETE FROM popular_movie WHERE id = :elem.identifier")
    abstract override fun delete(elem: Movie, onComplete: ITaskListener)

    @Query("DELETE FROM popular_movie")
    abstract override fun deleteAll(vararg elements: Movie, onComplete: ITaskListener)

}