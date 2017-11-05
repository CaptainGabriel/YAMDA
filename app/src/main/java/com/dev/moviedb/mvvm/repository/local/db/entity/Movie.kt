package com.dev.moviedb.mvvm.repository.local.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 *
 * @author PeteGabriel on 24/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */

data class Movie (
        @PrimaryKey val id: Long,
        var budget:String = "",
        var voteAverage:String = "",
        var backdropPath:String = "",
        var genres: List<Genres>? = null,
        var status:String = "",
        var runtime:String = "",
        var spokenLanguages:List<SpokenLanguages>? = null,
        var adult:String = "",
        var homepage:String = "",
        var productionCountries:List<ProductionCountries>? = null,
        var title:String = "",
        var originalLanguage:String = "",
        var overview:String = "",
        var productionCompanies:List<ProductionCompanies>? = null,
        var imdbId:String = "",
        var releaseDate:String = "",
        var originalTitle:String = "",
        var voteCount:String = "",
        var video:String = "",
        var tagLine:String = "",
        var revenue:String = "",
        var popularity:String = "")


data class Genres(val id: String, val name: String)

data class ProductionCompanies(val id: String, val name: String)

data class ProductionCountries(val name: String)

data class SpokenLanguages(val name: String)



@Entity(tableName = "now_playing_movie")
//TODO might be need a converter here
data class NowPlayingMovie(
    @Embedded(prefix = "nplay")
    var movie: Movie)

@Entity(tableName = "top_rated_movie")
//TODO might be need a converter here
data class TopRatedMovie(
        @Embedded(prefix = "topr")
        var movie: Movie)

@Entity(tableName = "popular_movie")
//TODO might be need a converter here
data class PopularMovie(
        @Embedded(prefix = "popular")
        var movie: Movie)

