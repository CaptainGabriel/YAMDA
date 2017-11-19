package com.dev.moviedb.mvvm.components.front.popular_tab

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.model.Movie
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadUrl
import kotlinx.android.synthetic.main.item_movie_generic_layout.view.*
import petegabriel.com.yamda.R

/**
 * The adapter to show the list of the most top rated movies.
 *
 * Yamda 1.0.0.
 */
class TopRatedMoviesListAdapter: AbstractMovieItemAdapter<TopRatedMoviesListAdapter.TopRatedMovieViewHolder>(){


    override fun onBindViewHolder(holder: TopRatedMovieViewHolder?, position: Int) {
        holder?.bind(movies?.results?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TopRatedMovieViewHolder =
            TopRatedMovieViewHolder(parent?.inflate(R.layout.item_movie_generic_layout)!!)


    class TopRatedMovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item: Movie?) = with(itemView){
            movieTitleTxtView.text = item?.primaryFacts?.originalTitle?.formatMovieCardName()
            itemImageFrame.loadUrl(item?.movieImages?.posterImagePath!!)
        }

    }
}