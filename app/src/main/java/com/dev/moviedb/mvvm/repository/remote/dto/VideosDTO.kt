package com.dev.moviedb.mvvm.repository.remote.dto;

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Yamda 1.0.0.
 */
data class VideosDTO(var results: Array<ResultsDTO>): Parcelable
{
    constructor(parcel: Parcel) : this(parcel.createTypedArray(ResultsDTO)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VideosDTO

        if (!Arrays.equals(results, other.results)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(results)
    }

    companion object CREATOR : Parcelable.Creator<VideosDTO> {
        override fun createFromParcel(parcel: Parcel): VideosDTO {
            return VideosDTO(parcel)
        }

        override fun newArray(size: Int): Array<VideosDTO?> {
            return arrayOfNulls(size)
        }
    }
}