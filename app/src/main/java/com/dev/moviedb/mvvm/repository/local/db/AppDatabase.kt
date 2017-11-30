package com.dev.moviedb.mvvm.repository.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dev.moviedb.mvvm.repository.local.db.dao.NowPlayingDao
import com.dev.moviedb.mvvm.repository.local.db.dao.PopularDao
import com.dev.moviedb.mvvm.repository.local.db.dao.TopRatedDao
import com.dev.moviedb.mvvm.repository.local.db.entity.NowPlayingMovieWrapper
import com.dev.moviedb.mvvm.repository.local.db.entity.PopularMovieWrapper
import com.dev.moviedb.mvvm.repository.local.db.entity.TopRatedMovieWrapper


/**
 *
 *
 * Yamda 1.0.0
 */
@Database(entities =
arrayOf(TopRatedMovieWrapper::class,
        PopularMovieWrapper::class,
        NowPlayingMovieWrapper::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun popularDao(): PopularDao

    abstract fun topRatedDao(): TopRatedDao

    abstract fun nowPlayingDao(): NowPlayingDao

    companion object {

        val databaseName = "yamda-db"

        private val instance: AppDatabase? = null

    }



}