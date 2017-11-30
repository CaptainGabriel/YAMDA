package com.dev.moviedb.mvvm.repository.local.db.entity.content

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents data that describes a certain movie in a more detailed way.
 *
 * Yamda 1.0.0.
 */
data class AdvancedData(
        var imdbId: String,
        var runtime: Int,
        var tagLine: String,
        var homepage: String,
        var genres: Genre,
        var releaseDate: String
): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(Genre::class.java.classLoader),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imdbId)
        parcel.writeInt(runtime)
        parcel.writeString(tagLine)
        parcel.writeString(homepage)
        parcel.writeParcelable(genres, flags)
        parcel.writeString(releaseDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdvancedData> {
        override fun createFromParcel(parcel: Parcel): AdvancedData {
            return AdvancedData(parcel)
        }

        override fun newArray(size: Int): Array<AdvancedData?> {
            return arrayOfNulls(size)
        }
    }

}