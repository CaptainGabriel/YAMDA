package com.dev.moviedb.mvvm.modelstate.movies.mapper

import com.dev.moviedb.mvvm.modelstate.movies.content.Movie
import com.dev.moviedb.mvvm.modelstate.movies.dto.MovieDTO

/**
 * Decorator class that implements advanced aspects of the mapping
 * logic between dtos and model classes.
 *
 * Yamda 1.0.0.
 */
abstract class MovieMapperDecorator(private val delegate : MovieMapper) : MovieMapper{


    override fun toMovie(movieDto : MovieDTO): Movie {
        var mov = this.delegate.toMovie(movieDto)

        //TODO add custom logic here

        movieDto.trailers?.run{
            if (this.youtube.isNotEmpty()){
                mov.movieTrailer.addTrailer(this.youtube[0].source)
            }
        }

        //mov.advancedFacts.

        return mov
    }

}