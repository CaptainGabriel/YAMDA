package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.model.TopRatedModel

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Dao
abstract class TopRatedDao : IDao<TopRatedModel>{

    @Query("SELECT * FROM top_rated_movie")
    abstract fun fetchAll(): List<TopRatedModel>
}