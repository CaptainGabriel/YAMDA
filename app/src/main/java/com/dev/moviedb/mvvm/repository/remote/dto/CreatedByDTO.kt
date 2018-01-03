package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
class CreatedByDTO() : Parcelable{

    var id: String? = ""

    var name: String? = ""

    var gender: String? = ""

    var profile_path: String? = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        gender = parcel.readString()
        profile_path = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(gender)
        parcel.writeString(profile_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CreatedByDTO> {
        override fun createFromParcel(parcel: Parcel): CreatedByDTO {
            return CreatedByDTO(parcel)
        }

        override fun newArray(size: Int): Array<CreatedByDTO?> {
            return arrayOfNulls(size)
        }
    }
}