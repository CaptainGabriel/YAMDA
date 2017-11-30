package com.dev.moviedb.mvvm.repository.local.db.entity

import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.dev.moviedb.mvvm.repository.local.db.entity.content.*
import com.dev.moviedb.mvvm.repository.local.model.IdentifiableModel

/**
 *
 *
 * @author PeteGabriel on 24/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */

class MovieEntity(var primaryFact: PrimaryData,
                  var movieImages: MovieImage,
                  var popularity: Popularity,
                  var advancedData: AdvancedData,
                  var movieTrailer: MovieTrailer): IdentifiableModel, Parcelable{

    @PrimaryKey var id: Long = 0

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(PrimaryData::class.java.classLoader),
            parcel.readParcelable(MovieImage::class.java.classLoader),
            parcel.readParcelable(Popularity::class.java.classLoader),
            parcel.readParcelable(AdvancedData::class.java.classLoader),
            parcel.readParcelable(MovieTrailer::class.java.classLoader)) {
        id = parcel.readLong()
    }

    override fun getIdentifier(): Long = id
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeParcelable(primaryFact, flags)
        parcel.writeParcelable(movieImages, flags)
        parcel.writeParcelable(popularity, flags)
        parcel.writeParcelable(advancedData, flags)
        parcel.writeParcelable(movieTrailer, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieEntity> {
        override fun createFromParcel(parcel: Parcel): MovieEntity {
            return MovieEntity(parcel)
        }

        override fun newArray(size: Int): Array<MovieEntity?> {
            return arrayOfNulls(size)
        }
    }


}


