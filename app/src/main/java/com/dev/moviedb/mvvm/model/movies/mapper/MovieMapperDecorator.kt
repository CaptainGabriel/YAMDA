package com.dev.moviedb.mvvm.model.movies.mapper

import com.dev.moviedb.mvvm.model.movies.Movie
import com.dev.moviedb.mvvm.model.movies.MovieTrailer
import com.dev.moviedb.mvvm.model.movies.dto.MovieDTO

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

        //TODO Read about _with_ operator
        with(movieDto.trailers){
            if (this.youtube.size > 0){
                val trailer = MovieTrailer()
                trailer.addTrailer(this.youtube[0].source)
                mov.setMovieTrailers(trailer)
            }
        }

        mov.advancedFacts.

        return mov
    }

}