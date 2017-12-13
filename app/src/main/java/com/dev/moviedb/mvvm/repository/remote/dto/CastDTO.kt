package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class CastDTO(var id: String? = null,
              var order: String? = null,
              var credit_id: String? = null,
              var name: String? = null,
              var gender: String? = null,
              var cast_id: String? = null,
              var profile_path: String? = null,
              var character: String? = null): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(order)
        parcel.writeString(credit_id)
        parcel.writeString(name)
        parcel.writeString(gender)
        parcel.writeString(cast_id)
        parcel.writeString(profile_path)
        parcel.writeString(character)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CastDTO> {
        override fun createFromParcel(parcel: Parcel): CastDTO {
            return CastDTO(parcel)
        }

        override fun newArray(size: Int): Array<CastDTO?> {
            return arrayOfNulls(size)
        }
    }
}