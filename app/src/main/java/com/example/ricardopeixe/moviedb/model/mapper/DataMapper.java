package com.example.ricardopeixe.moviedb.model.mapper;

import com.example.ricardopeixe.moviedb.model.AdvancedFact;
import com.example.ricardopeixe.moviedb.model.Genres;
import com.example.ricardopeixe.moviedb.model.Movie;
import com.example.ricardopeixe.moviedb.model.MovieAggregator;
import com.example.ricardopeixe.moviedb.model.MovieImages;
import com.example.ricardopeixe.moviedb.model.MovieTrailer;
import com.example.ricardopeixe.moviedb.model.Popularity;
import com.example.ricardopeixe.moviedb.model.PrimaryFact;
import com.example.ricardopeixe.moviedb.model.dto.MovieAggregatorDTO;
import com.example.ricardopeixe.moviedb.model.dto.MovieDTO;

import android.support.annotation.NonNull;

/**
 * Class whose instances are used to map between instances of simple classes and instances of
 * DTO classes present inside the package {@link com.example.ricardopeixe.moviedb.model.dto}.
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