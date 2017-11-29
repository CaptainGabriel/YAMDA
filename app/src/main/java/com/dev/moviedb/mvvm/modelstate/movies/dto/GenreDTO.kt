package com.dev.moviedb.mvvm.modelstate.movies.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class GenreDTO(val id: Int, val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GenreDTO> {
        override fun createFromParcel(parcel: Parcel): GenreDTO {
            return GenreDTO(parcel)
        }

        override fun newArray(size: Int): Array<GenreDTO?> {
            return arrayOfNulls(size)
        }
    }
}