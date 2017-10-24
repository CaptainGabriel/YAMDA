package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.ITaskListener
import com.dev.moviedb.mvvm.repository.local.db.entity.Movie

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Dao
abstract class NowPlayingDao : IDao<Movie>{


    @Query("SELECT * FROM now_playing_movie")
    abstract override fun insert(elem: Movie, onComplete: ITaskListener)

    @Query("SELECT * FROM now_playing_movie")
    abstract override fun insertAll(vararg elements: Movie, onComplete: ITaskListener)

    @Query("UPDATE FROM now_playing_movie WHERE id = :elem.identifier")
    abstract override fun update(elem: Movie, onComplete: ITaskListener)

    @Query("UPDATE FROM now_playing_movie")
    abstract override fun updateAll(vararg elements: Movie, onComplete: ITaskListener)

    @Query("DELETE FROM now_playing_movie WHERE id = :elem.identifier")
    abstract override fun delete(elem: Movie, onComplete: ITaskListener)

    @Query("DELETE FROM now_playing_movie")
    abstract override fun deleteAll(vararg elements: Movie, onComplete: ITaskListener)

}