package com.dev.moviedb.mvvm.model.movies;


import java.util.List;

/**
 *
 * POJO that represents the concept of a type of response from the API in the client side.
 * Composed by a sequence of results, current page returned by the API,
 * total number of pages and total number of results.
 *
 * @version 0.0.2
 */
public class MovieAggregator {

    private int mPage;

    private List<Movie> mResults;

    private int mTotalPages;

    private int mTotalResults;

    private MovieAggregator(AggregatorBuilder builder){
        mPage = builder.page;
        mResults = builder.results;
        mTotalPages = builder.total_pages;
        mTotalResults = builder.total_results;
    }


    public int getmPage() {
        return mPage;
    }

    public int getmTotalPages() {
        return mTotalPages;
    }

    public int getmTotalResults() {
        return mTotalResults;
    }

    public List<Movie> getmResults() {
        return mResults;
    }

    public static class AggregatorBuilder{
        private int page;
        private List<Movie> results;
        private int total_pages;
        private int total_results;

        public AggregatorBuilder(){}

        public AggregatorBuilder setPage(int page){
            this.page = page;
            return this;
        }

        public AggregatorBuilder setResults(List<Movie> results){
            this.results = results;
            return this;
        }

        public AggregatorBuilder setTotalPages(int total){
            this.total_pages = total;
            return this;
        }

        public AggregatorBuilder setTotalResults(int total){
            this.total_results = total;
            return this;
        }

        public MovieAggregator build(){
            return new MovieAggregator(this);
        }
    }

}
