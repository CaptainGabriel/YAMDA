package com.dev.moviedb.mvvm.repository.remote.dto

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Images that belong to a movie.
 *
 * Yamda 1.0.0.
 */
class ResultImageDTO() :Parcelable{

    @SerializedName("aspect_ratio")
    @Expose
    var aspectRatio: Float = 0.0f

    @SerializedName("file_path")
    @Expose
    var filePath: String = ""

    var height: Float = 0.0f

    @SerializedName("vote_average")
    @Expose
    var voteAverage: Float = 0.0f

    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0

    var width: Int = 0

    constructor(parcel: Parcel) : this() {
        aspectRatio = parcel.readFloat()
        filePath = parcel.readString()
        height = parcel.readFloat()
        voteAverage = parcel.readFloat()
        voteCount = parcel.readInt()
        width = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(aspectRatio)
        parcel.writeString(filePath)
        parcel.writeFloat(height)
        parcel.writeFloat(voteAverage)
        parcel.writeInt(voteCount)
        parcel.writeInt(width)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultImageDTO> {
        override fun createFromParcel(parcel: Parcel): ResultImageDTO {
            return ResultImageDTO(parcel)
        }

        override fun newArray(size: Int): Array<ResultImageDTO?> {
            return arrayOfNulls(size)
        }
    }

}