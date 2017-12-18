package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class ImagesDTO(var backdrops: Array<ResultImageDTO>, var posters: Array<ResultImageDTO>) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.createTypedArray(ResultImageDTO),
            parcel.createTypedArray(ResultImageDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedArray(backdrops, flags)
        parcel.writeTypedArray(posters, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImagesDTO> {
        override fun createFromParcel(parcel: Parcel): ImagesDTO {
            return ImagesDTO(parcel)
        }

        override fun newArray(size: Int): Array<ImagesDTO?> {
            return arrayOfNulls(size)
        }
    }
}