package com.dev.moviedb.mvvm.modelstate.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 * Represents the concept in the client side of the movie concept held by the Api.
 * Yamda 1.0.0.
 */
data class Movie(var primaryFact: PrimaryData,
                 var movieImages: MovieImage,
                 var popularity: Popularity,
                 var advancedData: AdvancedData,
                 var movieTrailer: MovieTrailer): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(PrimaryData::class.java.classLoader),
            parcel.readParcelable(MovieImage::class.java.classLoader),
            parcel.readParcelable(Popularity::class.java.classLoader),
            parcel.readParcelable(AdvancedData::class.java.classLoader),
            parcel.readParcelable(MovieTrailer::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(primaryFact, flags)
        parcel.writeParcelable(movieImages, flags)
        parcel.writeParcelable(popularity, flags)
        parcel.writeParcelable(advancedData, flags)
        parcel.writeParcelable(movieTrailer, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}