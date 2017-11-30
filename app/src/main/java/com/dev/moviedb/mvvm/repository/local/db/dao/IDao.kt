package com.dev.moviedb.mvvm.repository.local.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

/**
 *
 *
 *Yamda 1.0.0
 */
interface IDao<in T>{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(elem: T, onComplete: () -> Unit)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
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