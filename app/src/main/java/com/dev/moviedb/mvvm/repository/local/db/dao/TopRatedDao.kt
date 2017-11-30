package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.db.entity.TopRatedMovieWrapper
import io.reactivex.Single

/**
 *
 * Yamda 1.0.0
 */
@Dao
abstract class TopRatedDao: IDao<TopRatedMovieWrapper> {

    @Query("SELECT * FROM top_rated_movie")
    abstract fun fetchAll(): Single<List<TopRatedMovieWrapper>>
}