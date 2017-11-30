package com.dev.moviedb.mvvm.repository.local.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import com.dev.moviedb.mvvm.repository.local.model.IdentifiableModel

/**
 *
 * Yamda 1.0.0.
 */
@Entity(tableName = "popular_movie")
class PopularMovieWrapper (@Embedded(prefix = "popular") var movie: MovieEntity): IdentifiableModel {
    override fun getIdentifier(): Long = movie.id
}