package com.dev.moviedb.mvvm.movies_tab

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.model.Movie
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadUrl
import kotlinx.android.synthetic.main.item_movie_generic_layout.view.*
import petegabriel.com.yamda.R

/**
 * The adapter to show the list of the most popular movies.
 *
 * Yamda 1.0.0.
 */
class PopularMoviesListAdapter : AbstractMovieItemAdapter<PopularMoviesListAdapter.PopularMovieViewHolder>() {

    override fun onBindViewHolder(holder: PopularMovieViewHolder?, position: Int) {
        holder?.bind(movies?.results?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PopularMovieViewHolder =
            PopularMovieViewHolder(parent?.inflate(R.layout.item_movie_generic_layout)!!)


    class PopularMovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Movie?) = with(itemView) {
            var maxCharsInName = 15
            movieTitleTxtView.text = item?.primaryFacts?.originalTitle?.formatMovieCardName()
            itemImageFrame.loadUrl(item?.movieImages?.posterImagePath!!)
            movieRatingValueTextView.text = "%.1f".format(item.popularity.voteAverage)
        }
    }

}