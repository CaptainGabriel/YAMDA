package com.dev.moviedb.mvvm.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.mvvm.extensions.formatMovieCardName
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadUrl
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import kotlinx.android.synthetic.main.item_movie_generic_layout.view.*
import petegabriel.com.yamda.R

/**
 *
 * Generic adapter used to display a movie item
 *
 * Yamda 1.0.0.
 */
class MovieDisplayAdapter : AbstractMovieItemAdapter<MovieDisplayAdapter.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieViewHolder?, position: Int) {
        holder?.bind(movies?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MovieViewHolder =
            MovieViewHolder(parent?.inflate(R.layout.item_movie_generic_layout)!!)


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieDTO?) = with(itemView) {
            //dto from tv shows and movies use different props for the name
            if (item?.title != null){
                movieTitleTxtView.text = item.title?.formatMovieCardName()
            }else if (item?.name != null){
                movieTitleTxtView.text = item.name?.formatMovieCardName()
            }

            itemImageFrame.loadUrl(item?.posterPath!!)
            movieRatingValueTextView.text = "%.1f".format(item.voteAverage)
        }
    }

}