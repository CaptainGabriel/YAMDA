package com.dev.moviedb.mvvm.model.movies.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class MovieCollectionDTO(var page: Int,
                              var results:  List<MovieDTO>,
                              var totalPages: Int,
                              var totalResults: Int): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(MovieDTO.CREATOR),
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeTypedList(results)
        parcel.writeInt(totalPages)
        parcel.writeInt(totalResults)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieCollectionDTO> {
        override fun createFromParcel(parcel: Parcel): MovieCollectionDTO {
            return MovieCollectionDTO(parcel)
        }

        override fun newArray(size: Int): Array<MovieCollectionDTO?> {
            return arrayOfNulls(size)
        }
    }
}