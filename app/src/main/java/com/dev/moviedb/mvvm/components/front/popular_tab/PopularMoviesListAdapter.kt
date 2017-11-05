package com.dev.moviedb.mvvm.components.front.popular_tab

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.model.Movie
import com.dev.moviedb.model.dto.MovieCollectionDto
import com.dev.moviedb.mvvm.components.front.popular_tab.PopularMoviesListAdapter.PopularMovieViewHolder
import com.dev.moviedb.mvvm.utils.inflate
import kotlinx.android.synthetic.main.item_movie_generic_layout.view.*
import petegabriel.com.yamda.R

/**
 *
 *
 * @author PeteGabriel on 05/11/2017
 * Copyright (c) 2017
 * All rights reserved.
 */
class PopularMoviesListAdapter : RecyclerView.Adapter<PopularMovieViewHolder>() {

    private var movies: MovieCollectionDto? = null

    override fun onBindViewHolder(holder: PopularMovieViewHolder?, position: Int) {
        holder?.bind(movies?.results?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PopularMovieViewHolder =
        PopularMovieViewHolder(parent?.inflate(R.layout.item_movie_generic_layout)!!)


    override fun getItemCount(): Int {
        return if (movies == null) 0 else movies?.results?.size!!
    }


    fun adNewData(movies: MovieCollectionDto){
        this.movies = movies
    }



    class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: Movie?) = with(itemView) {
            movieTitleTxtView.text = item?.primaryFacts?.originalTitle
        }


    }


}