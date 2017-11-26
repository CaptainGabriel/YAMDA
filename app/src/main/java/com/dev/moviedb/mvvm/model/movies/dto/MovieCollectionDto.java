package com.dev.moviedb.mvvm.model.movies.dto;

import com.dev.moviedb.mvvm.model.movies.Movie;
import com.dev.moviedb.mvvm.model.movies.mapper.DataMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO that represents the most normal results obtained via API requests.
 * Composed by a sequence of results, current page returned by the API,
 * total number of pages and total number of results.
 *
 * @version 0.0.1
 */
public class MovieCollectionDto {

    private int page;

    private List<MovieDTO> results;

    private int total_pages;

    private int total_results;

    public MovieCollectionDto(int page, List<MovieDTO> results, int totalPages, int totalResults) {
        this.page = page;
        this.results = results;
        this.total_pages = totalPages;
        this.total_results = totalResults;
    }


    public int getPage() {
        return page;
    }

    public int getTotalPages() {
        return total_pages;
    }

    public int getTotalResults() {
        return total_results;
    }

    public List<Movie> getResults() {
        List<Movie> tmpList = new ArrayList<>();
        DataMapper mapper = new DataMapper();
        for (MovieDTO dto : results){
            tmpList.add(mapper.convertFrom(dto));
        }
        return tmpList;
    }

    /**
     * To support 0.0.1 version. Used by the callbacks that talk directly to the
     * retrofit library.
     *
     */
    @Deprecated
    public List<MovieDTO> getSimpleResults() {
        return this.results;
    }
}
