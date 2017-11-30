package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.db.entity.PopularMovieWrapper
import io.reactivex.Single

/**
 *
 * Yamda 1.0.0
 */
@Dao
abstract class PopularDao: IDao<PopularMovieWrapper>{

    @Query("SELECT * FROM popular_movie")
    abstract fun fetchAll(): Single<List<PopularMovieWrapper>>

    //TODO add fetch by ID

}