package com.dev.moviedb.mvvm.modelstate.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 *
 * Yamda 1.0.0.
 */
data class Genre (var genreNames: List<String>): Parcelable {


    fun getGenreNames(): String{
        var delim = ""
        var toConcat = ""
        for (gName in genreNames) {
            toConcat = "$toConcat$delim$gName"
            delim = ","
        }
        return toConcat
    }


    constructor(parcel: Parcel) : this(parcel.createStringArrayList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(genreNames)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Genre> {
        override fun createFromParcel(parcel: Parcel): Genre {
            return Genre(parcel)
        }

        override fun newArray(size: Int): Array<Genre?> {
            return arrayOfNulls(size)
        }
    }
}