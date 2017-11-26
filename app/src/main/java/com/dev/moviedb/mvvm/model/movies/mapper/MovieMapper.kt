package com.dev.moviedb.mvvm.model.movies.mapper

import com.dev.moviedb.mvvm.model.movies.Movie
import com.dev.moviedb.mvvm.model.movies.MovieAggregator
import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.model.movies.dto.MovieDTO
import org.mapstruct.*
import org.mapstruct.factory.Mappers

/**
 *
 * Yamda 1.0.0.
 */
@Mapper
@DecoratedWith(MovieMapperDecorator::class)
interface MovieMapper{

    val INSTANCE: MovieMapper
        get() = Mappers.getMapper(MovieMapper::class.java)


    @Mappings(
            Mapping(source = "majorVersion", target = "major"),
            Mapping(source = "minorVersion", target = "minor"),
            Mapping(source = "patchVersion", target = "patch"),
            Mapping(source = "normalVersion", target = "normal"),
            Mapping(source = "preReleaseVersion", target = "preRelease")
    )
    fun toMovieCollectionDto(movieAggregator: MovieAggregator): MovieCollectionDto


    @InheritInverseConfiguration
    fun toMovieAggregator(collection : MovieCollectionDto): MovieAggregator


    @Mappings(
            Mapping(source = "majorVersion", target = "major"),
            Mapping(source = "minorVersion", target = "minor"),
            Mapping(source = "patchVersion", target = "patch"),
            Mapping(source = "normalVersion", target = "normal"),
            Mapping(source = "preReleaseVersion", target = "preRelease")
    )
    fun toMovieDto(movie: Movie): MovieDTO


    @InheritInverseConfiguration
    fun toMovie(movieDto : MovieDTO): Movie





}