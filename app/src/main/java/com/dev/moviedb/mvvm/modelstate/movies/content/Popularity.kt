package com.dev.moviedb.mvvm.modelstate.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 * This class represents the data that describes the popularity of a certain
 * movie.
 *
 * Yamda 1.0.0.
 */
data class Popularity(var voteAverage: Double, var popularity: Double, var voteCount: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(voteAverage)
        parcel.writeDouble(popularity)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Popularity> {
        override fun createFromParcel(parcel: Parcel): Popularity {
            return Popularity(parcel)
        }

        override fun newArray(size: Int): Array<Popularity?> {
            return arrayOfNulls(size)
        }
    }
}