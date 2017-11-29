package com.dev.moviedb.mvvm.model.movies.content

/**
 * POJO that represents the concept of a type of response from the API in the client side.
 * Composed by a sequence of results, current page returned by the API,
 * total number of pages and total number of results.
 *
 * Yamda 1.0.0.
 */
data class MovieAggregator (var page: Int, var results: List<Movie>, var totalPages: Int, var totalResults: Int)

