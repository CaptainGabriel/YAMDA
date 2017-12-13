package com.dev.moviedb.mvvm.repository.remote.dto;

import android.os.Parcel
import android.os.Parcelable

/**
 * Yamda 1.0.0.
 */

data class ResultsDTO(
    var site: String,
    var id: String,
    var name: String,
    var type: String,
    var key: String,
    var size: String): Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(site)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(key)
        parcel.writeString(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultsDTO> {
        override fun createFromParcel(parcel: Parcel): ResultsDTO {
            return ResultsDTO(parcel)
        }

        override fun newArray(size: Int): Array<ResultsDTO?> {
            return arrayOfNulls(size)
        }
    }
}
