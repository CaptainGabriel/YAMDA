package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class TraillerDTO(var youtube: List<YoutubeVideoDTO>): Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(YoutubeVideoDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(youtube)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TraillerDTO> {
        override fun createFromParcel(parcel: Parcel): TraillerDTO {
            return TraillerDTO(parcel)
        }

        override fun newArray(size: Int): Array<TraillerDTO?> {
            return arrayOfNulls(size)
        }
    }
}