package com.dev.moviedb.mvvm.model.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 * This class represents the data that is always available related
 * to a certain movie.
 *
 * Yamda 1.0.0.
 */
data class PrimaryData(var overview: String,
                       var originalTitle: String,
                       var title: String,
                       var id: Long,
                       var releaseDate: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(overview)
        parcel.writeString(originalTitle)
        parcel.writeString(title)
        parcel.writeLong(id)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PrimaryData> {
        override fun createFromParcel(parcel: Parcel): PrimaryData {
            return PrimaryData(parcel)
        }

        override fun newArray(size: Int): Array<PrimaryData?> {
            return arrayOfNulls(size)
        }
    }
}