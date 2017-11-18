package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.model.PopularModel

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Dao
abstract class PopularDao : IDao<PopularModel>{

    @Query("SELECT * FROM popular_movie")
    abstract fun fetchAll(): List<PopularModel>

    //TODO add fetch by ID

}