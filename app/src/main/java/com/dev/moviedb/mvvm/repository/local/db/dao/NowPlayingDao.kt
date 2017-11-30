package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.dev.moviedb.mvvm.repository.local.db.entity.NowPlayingMovieWrapper
import io.reactivex.Single

/**
 *
 *
 * Yamda 1.0.0
 */
@Dao
abstract class NowPlayingDao: IDao<NowPlayingMovieWrapper> {

    @Query("SELECT * FROM now_playing_movie")
    abstract fun fetchAll(): Single<List<NowPlayingMovieWrapper>>

    @Query("SELECT * FROM now_playing_movie WHERE id=:id")
    abstract fun fetchMovieById(id: Int): NowPlayingMovieWrapper
}