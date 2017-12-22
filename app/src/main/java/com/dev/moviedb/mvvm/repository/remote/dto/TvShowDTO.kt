package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 *
 * Yamda 1.0.0.
 */
class TvShowDTO() : Parcelable {

    @SerializedName("vote_average")
    @Expose
    private var voteAverage: String = ""

    private var genres: Array<GenreDTO>? = arrayOf<GenreDTO>()

    @SerializedName("episode_run_time")
    @Expose
    private var episodeRunTime: Array<String>? = arrayOf<String>()

    private var type: String = ""

    private var id: String = ""

    private var languages: Array<String>? = arrayOf<String>()

    @SerializedName("number_of_seasons")
    @Expose
    private var numberOfSeasons: String = ""

    @SerializedName("last_air_date")
    @Expose
    private var lastAirDate: String = ""

    @SerializedName("in_production")
    @Expose
    private var inProduction: String = ""

    private var name: String = ""

    private var popularity: String = ""

    //private var networks: Array<Networks>? = null

    @SerializedName("created_by")
    @Expose
    private var createdBy: Array<CreatedByDTO>? = arrayOf<CreatedByDTO>()

    @SerializedName("backdrop_path")
    @Expose
    private var backdropPath: String = ""

    private var status: String = ""

    @SerializedName("number_of_episodes")
    @Expose
    private var numberOfEpisodes: String = ""

    @SerializedName("original_name")
    @Expose
    private var originalName: String = ""

    private var homepage: String = ""

    @SerializedName("first_air_date")
    @Expose
    private var firstAirDate: String = ""

    @SerializedName("origin_country")
    @Expose
    private var originCountry: Array<String>? = null

    @SerializedName("original_language")
    @Expose
    private var originalLanguage: String = ""

    private var overview: String = ""

    //private var production_companies: Array<Production_companies>? = null

    //private var seasons: Array<Seasons>? = null

    @SerializedName("vote_count")
    @Expose
    private var voteCount: String = ""

    @SerializedName("poster_path")
    @Expose
    private var posterPath: String = ""

    constructor(parcel: Parcel) : this() {
        voteAverage = parcel.readString()
        genres = parcel.createTypedArray(GenreDTO)
        episodeRunTime = parcel.createStringArray()
        type = parcel.readString()
        id = parcel.readString()
        languages = parcel.createStringArray()
        numberOfSeasons = parcel.readString()
        lastAirDate = parcel.readString()
        inProduction = parcel.readString()
        name = parcel.readString()
        popularity = parcel.readString()
        createdBy = parcel.createTypedArray(CreatedByDTO)
        backdropPath = parcel.readString()
        status = parcel.readString()
        numberOfEpisodes = parcel.readString()
        originalName = parcel.readString()
        homepage = parcel.readString()
        firstAirDate = parcel.readString()
        originCountry = parcel.createStringArray()
        originalLanguage = parcel.readString()
        overview = parcel.readString()
        voteCount = parcel.readString()
        posterPath = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
        parcel.writeString(backdropPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TvShowDTO> {
        override fun createFromParcel(parcel: Parcel): TvShowDTO {
            return TvShowDTO(parcel)
        }

        override fun newArray(size: Int): Array<TvShowDTO?> {
            return arrayOfNulls(size)
        }
    }


}