package com.dev.moviedb.model.mapper;

import android.support.annotation.NonNull;

import com.dev.moviedb.model.AdvancedFact;
import com.dev.moviedb.model.Genres;
import com.dev.moviedb.model.Movie;
import com.dev.moviedb.model.MovieAggregator;
import com.dev.moviedb.model.MovieImages;
import com.dev.moviedb.model.MovieTrailer;
import com.dev.moviedb.model.Popularity;
import com.dev.moviedb.model.PrimaryFact;
import com.dev.moviedb.model.dto.MovieAggregatorDTO;
import com.dev.moviedb.model.dto.MovieDTO;

/**
 * Class whose instances are used to map between instances of simple classes and instances of
 * DTO classes present inside the package {@link com.dev.moviedb.model.dto}.
 *
 * @author Paulo Pereira
 */
public class DataMapper {

    /**
     * Converts the given {@link MovieAggregatorDTO} instance to the corresponding {@link MovieAggregator}
     * instance.
     * @param dto The {@link MovieAggregatorDTO} instance to be converted.
     * @return The resulting {@link MovieAggregator} instance.
     */
    @NonNull
    public MovieAggregator convertFrom(@NonNull MovieAggregatorDTO dto) {
        return new MovieAggregator.AggregatorBuilder().setPage(dto.getPage())
                .setResults(dto.getResults())
                .setTotalPages(dto.getTotalPages())
                .setTotalResults(dto.getTotalResults())
                .build();
    }

    /**
     * Converts the given {@link MovieDTO} instance to the corresponding {@link Movie}
     * instance.
     * @param dto The {@link MovieDTO} instance to be converted.
     * @return The resulting {@link Movie} instance.
     */
    @NonNull
    public Movie convertFrom(@NonNull MovieDTO dto) {

        PrimaryFact facts = new PrimaryFact(dto.getOverview(),
                dto.getTitle(), dto.getTitle(), (long) dto.getId(), dto.getReleaseDate());

        Genres genres = new Genres(dto.getGenres());
        AdvancedFact aFacts = null;
        aFacts = new AdvancedFact(dto.getImdb(), dto.getRuntime(), dto.getTagline(),
                    facts.getReleaseDate(), dto.getHomepage(), genres);

        //this should have been different however we did enough just to provide one trailer.
        MovieTrailer mt = null;
        if(dto.getTrailers() != null && dto.getTrailers().getYoutube().size() > 0) {
            mt = new MovieTrailer();
            mt.addTrailer(dto.getTrailers().getYoutube().get(0).getSource());
        }

        Movie tmp = new Movie(facts);
        tmp.setPopularity(new Popularity(dto.getVoteAverage(), dto.getPopularity(), dto.getVoteCount()));
        tmp.setMovieImages(new MovieImages(dto.getImageBackdropPath(), dto.getPosterPath()));
        tmp.setAdvancedFacts(aFacts);
        tmp.setMovieTrailers(mt);
        return tmp;
    }


}