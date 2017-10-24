package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update
import com.dev.moviedb.mvvm.repository.ITaskListener

/**
 *
 *
 * @author PeteGabriel on 23/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
interface IDao<in T>{

    @Insert
    fun insert(elem: T, onComplete: ITaskListener)

    @Insert
    fun insertAll(vararg elements: T, onComplete: ITaskListener)

    @Update
    fun update(elem: T, onComplete: ITaskListener)

    @Update
    fun updateAll(vararg elements: T, onComplete: ITaskListener)

    @Delete
    fun delete(elem: T, onComplete: ITaskListener)

    @Delete
    fun deleteAll(vararg elements: T, onComplete: ITaskListener)

}