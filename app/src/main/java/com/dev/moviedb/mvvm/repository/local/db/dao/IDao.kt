package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
interface IDao<in T>{

    @Insert
    fun insert(elem: T, onComplete: () -> Unit)

    @Insert
    fun insertAll(vararg elements: T, onComplete: () -> Unit)

    @Update
    fun update(elem: T, onComplete: () -> Unit)

    @Update
    fun updateAll(vararg elements: T, onComplete: () -> Unit)

    @Delete
    fun delete(elem: T, onComplete: () -> Unit)

    @Delete
    fun deleteAll(vararg elements: T, onComplete: () -> Unit)

}