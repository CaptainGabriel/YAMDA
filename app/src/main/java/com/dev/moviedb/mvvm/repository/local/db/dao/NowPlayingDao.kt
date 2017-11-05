package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.db.entity.NowPlayingMovie

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Dao
abstract class NowPlayingDao : IDao<NowPlayingMovie>{

    @Query("SELECT * FROM now_playing_movie")
    abstract fun fetchAll(): List<NowPlayingMovie>
}