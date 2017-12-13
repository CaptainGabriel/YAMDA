package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 *
 * Yamda 1.0.0.
 */
data class CreditsDTO(var cast: Array<CastDTO>? = null) : Parcelable{

    constructor(parcel: Parcel) : this(parcel.createTypedArray(CastDTO)) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditsDTO

        if (!Arrays.equals(cast, other.cast)) return false

        return true
    }

    override fun hashCode(): Int {
        return cast?.let { Arrays.hashCode(it) } ?: 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedArray(cast, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreditsDTO> {
        override fun createFromParcel(parcel: Parcel): CreditsDTO {
            return CreditsDTO(parcel)
        }

        override fun newArray(size: Int): Array<CreditsDTO?> {
            return arrayOfNulls(size)
        }
    }

}