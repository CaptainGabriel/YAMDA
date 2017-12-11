package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * DTO that represents the concept of movie held by the Api.
 *
 * Yamda 1.0.0.
 */
class MovieDTO() : Parcelable {

    @SerializedName("backdrop_path")
    @Expose
    var backdropPath: String? = ""

    var id: Int = 0

    /**
     * The main overview
     */
    var overview: String = ""

    /**
     * The release date
     */
    @SerializedName("release_date")
    @Expose
    var releaseDate: String = ""

    /** The poster path */
    @SerializedName("poster_path")
    @Expose
    var posterPath: String? = ""


    /**
     * MovieEntity's popularity
     */
    var popularity: Double = 0.0

    /**
     * The movie title
     */
    var title: String? = ""

    /**
     * The tv show's name
     */
    var name: String? = ""

    /**
     * If has video or not
     */
    var video: Boolean = false

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Double = 0.0

    /**
     * The current status
     */
    var status: String = ""

    /**
     * The runtime length
     */
    var runtime: Int = 0

    @SerializedName("tagline")
    @Expose
    var tagline: String = ""

    @SerializedName("imdb_id")
    @Expose
    var imdbId: String = ""

    var homepage: String = ""

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0

    var videos: Videos? = null

    var genres: List<GenreDTO>? = null

    constructor(parcel: Parcel) : this() {
        backdropPath = parcel.readString()
        id = parcel.readInt()
        overview = parcel.readString()
        releaseDate = parcel.readString()
        posterPath = parcel.readString()
        popularity = parcel.readDouble()
        title = parcel.readString()
        name = parcel.readString()
        video = parcel.readByte() != 0.toByte()
        voteAverage = parcel.readDouble()
        status = parcel.readString()
        runtime = parcel.readInt()
        tagline = parcel.readString()
        imdbId = parcel.readString()
        homepage = parcel.readString()
        voteCount = parcel.readInt()
        genres = parcel.createTypedArrayList(GenreDTO)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeInt(id)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeString(posterPath)
        parcel.writeDouble(popularity)
        parcel.writeString(title)
        parcel.writeString(name)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeDouble(voteAverage)
        parcel.writeString(status)
        parcel.writeInt(runtime)
        parcel.writeString(tagline)
        parcel.writeString(imdbId)
        parcel.writeString(homepage)
        parcel.writeInt(voteCount)
        parcel.writeTypedList(genres)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieDTO> {
        override fun createFromParcel(parcel: Parcel): MovieDTO {
            return MovieDTO(parcel)
        }

        override fun newArray(size: Int): Array<MovieDTO?> {
            return arrayOfNulls(size)
        }
    }


}