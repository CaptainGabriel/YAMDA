package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.model.NowPlayingModel

/**
 *
 *
 * Yamda 1.0.0
 */
@Dao
abstract class NowPlayingDao : IDao<NowPlayingModel>{

    @Query("SELECT * FROM now_playing_movie")
    abstract fun fetchAll(): List<NowPlayingModel>

    @Query("SELECT * FROM now_playing_movie WHERE id=:id")
    abstract fun fetchMovieById(id: Int): NowPlayingModel
}