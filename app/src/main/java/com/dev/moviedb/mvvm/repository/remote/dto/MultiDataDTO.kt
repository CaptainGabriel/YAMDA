package com.dev.moviedb.mvvm.repository.remote.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MultiDataDTO {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
    @SerializedName("total_results")
    @Expose
    var totalResults: Int? = null
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int? = null


    override fun toString(): String {
        return "MultiDataDTO -> Page: $page, Total Results: $totalResults, Total Pages: $totalPages;"
    }

    fun Result.isPerson(): Boolean = isMediaType("person")

    fun Result.isMovie(): Boolean = isMediaType("movie")

    fun Result.isTvShow(): Boolean = isMediaType("tv")

    private fun Result.isMediaType(type: String): Boolean {
        this.mediaType?.let {
            return it.equals(type, true)
        }
        return false
    }

}
