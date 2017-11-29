package com.dev.moviedb.mvvm.model.movies.content

import android.os.Parcel
import android.os.Parcelable

/**
 * This class represents the movie trailers associated with a certain movie.
 *
 * Yamda 1.0.0.
 */
class MovieTrailler():Parcelable {

    private val YOUTUBE_HOST = "https://www.youtube.com/watch?v=%s&amp;feature=youtube_gdata";

    private var trailersLink : MutableList<String> = ArrayList()

    fun addTrailer(path: String){
        trailersLink.add("$YOUTUBE_HOST$path")
    }

    fun getTrailers(): Iterable<String> = trailersLink.asIterable()

    constructor(parcel: Parcel) : this() {
        parcel.readStringList(trailersLink)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(trailersLink)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieTrailler> {
        override fun createFromParcel(parcel: Parcel): MovieTrailler {
            return MovieTrailler(parcel)
        }

        override fun newArray(size: Int): Array<MovieTrailler?> {
            return arrayOfNulls(size)
        }
    }
}