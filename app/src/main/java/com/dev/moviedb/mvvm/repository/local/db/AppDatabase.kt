package com.dev.moviedb.mvvm.repository.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.dev.moviedb.mvvm.repository.local.db.dao.NowPlayingDao
import com.dev.moviedb.mvvm.repository.local.db.dao.PopularDao
import com.dev.moviedb.mvvm.repository.local.db.dao.TopRatedDao
import com.dev.moviedb.mvvm.repository.local.db.entity.Movie


/**
 *
 *
 * @author PeteGabriel on 24/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
@Database(entities = arrayOf(Movie::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun popularDao(): PopularDao

    abstract fun topRatedDao(): TopRatedDao

    abstract fun nowPlayingDao(): NowPlayingDao

    companion object {

        val databaseName = "yamda-db"

        private val instance: AppDatabase? = null

    }



}