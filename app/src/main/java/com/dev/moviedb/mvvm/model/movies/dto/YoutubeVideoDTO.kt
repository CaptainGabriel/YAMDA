package com.dev.moviedb.mvvm.model.movies.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class YoutubeVideoDTO(var name: String, var size: String, var source: String, var type: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(size)
        parcel.writeString(source)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<YoutubeVideoDTO> {
        override fun createFromParcel(parcel: Parcel): YoutubeVideoDTO {
            return YoutubeVideoDTO(parcel)
        }

        override fun newArray(size: Int): Array<YoutubeVideoDTO?> {
            return arrayOfNulls(size)
        }
    }
}