package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.dev.moviedb.mvvm.extensions.readBoolean
import com.dev.moviedb.mvvm.extensions.writeBoolean
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
        get() = field?.substring(1)

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
        get() = field?.substring(1)

    /**
     * MovieEntity's popularity
     */
    var popularity: Double = 0.0

    /**
     * The movie title
     */
    var title: String? = null

    /**
     * The tv show's name
     */
    var name: String? = null

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
        video = parcel.readBoolean()!!
        voteAverage = parcel.readDouble()
        status = parcel.readString()
        runtime = parcel.readInt()
        tagline = parcel.readString()
        imdbId = parcel.readString()
        homepage = parcel.readString()
        voteCount = parcel.readInt()
        //trailers = parcel.createTypedArrayList(Results.CREATOR)
        genres = parcel.createTypedArrayList(GenreDTO.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(this.backdropPath)
        parcel.writeTypedList(genres)
        parcel.writeValue(this.id)
        parcel.writeString(this.overview)
        parcel.writeString(this.releaseDate)
        parcel.writeString(this.posterPath)
        parcel.writeValue(this.popularity)
        parcel.writeString(this.title)
        parcel.writeBoolean(this.video)
        parcel.writeValue(this.voteAverage)
        parcel.writeString(this.status)
        parcel.writeInt(this.runtime)
        parcel.writeString(this.tagline)
        parcel.writeString(this.imdbId)
        parcel.writeString(this.homepage)
        parcel.writeInt(this.voteCount)
        //parcel.writeParcelable(this.trailers, 0)
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