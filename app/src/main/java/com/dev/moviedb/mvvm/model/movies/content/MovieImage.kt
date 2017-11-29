package com.dev.moviedb.mvvm.model.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 * This class represents the info related to images associated with a certain movie.
 *
 * Yamda 1.0.0.
 */
data class MovieImage(var backdropImagePath: String, var posterImagePath: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropImagePath)
        parcel.writeString(posterImagePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieImage> {
        override fun createFromParcel(parcel: Parcel): MovieImage {
            return MovieImage(parcel)
        }

        override fun newArray(size: Int): Array<MovieImage?> {
            return arrayOfNulls(size)
        }
    }
}