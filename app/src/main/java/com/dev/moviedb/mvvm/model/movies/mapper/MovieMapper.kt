package com.dev.moviedb.mvvm.model.movies.mapper

import com.dev.moviedb.mvvm.model.movies.dto.MovieCollectionDTO
import com.dev.moviedb.mvvm.model.movies.dto.MovieDTO
import org.mapstruct.*

/**
 *
 * Interface responsible for specifying the mapping logic for entities
 * related with movies. Advanced aspects for that mapping logic should be implemented
 * in the class used as decorator.
 *
 * Yamda 1.0.0.
 */
@Mapper
@DecoratedWith(MovieMapperDecorator::class)
interface MovieMapper{

    @Mappings(
            Mapping(source = "majorVersion", target = "major"),
            Mapping(source = "minorVersion", target = "minor"),
            Mapping(source = "patchVersion", target = "patch"),
            Mapping(source = "normalVersion", target = "normal"),
            Mapping(source = "preReleaseVersion", target = "preRelease")
    )
    fun toMovieCollectionDto(movieAggregator: MovieAggregator): MovieCollectionDTO


    @InheritInverseConfiguration
    fun toMovieAggregator(collection : MovieCollectionDTO): MovieAggregator


    @Mappings(
            Mapping(source = "backdropPath", target = "mMovieImages.mBackdropImagePath"),
            Mapping(source = "id", target = "mPrimaryFacts.mId"),
            Mapping(source = "overview", target = "mPrimaryFacts.mOverview"),
            Mapping(source = "releaseDate", target = "mPrimaryFacts.mReleaseDate"),
            Mapping(source = "posterPath", target = "mMovieImages.mPosterImagePath"),
            Mapping(source = "popularity", target = "mPopularity.mPopularity"),
            //Mapping(source = "title", target = ""),
            //Mapping(source = "video", target = ""),
            Mapping(source = "voteAverage", target = "mPopularity.mVoteAverage"),
            //Mapping(source = "status", target = ""),
            Mapping(source = "runtime", target = "mAdvancedFacts.mRuntime"),
            Mapping(source = "tagline", target = "mAdvancedFacts.mTagLine"),
            Mapping(source = "imdbId", target = "mAdvancedFacts.mImdbID"),
            Mapping(source = "homepage", target = "mAdvancedFacts.mHomepage"),
            Mapping(source = "vote_count", target = "mPopularity.mVoteCount")
    //TODO trailers
    //TODO genres
    )
    fun toMovie(movieDto : MovieDTO): Movie


    @InheritInverseConfiguration
    fun toMovieDto(movie: Movie): MovieDTO

}