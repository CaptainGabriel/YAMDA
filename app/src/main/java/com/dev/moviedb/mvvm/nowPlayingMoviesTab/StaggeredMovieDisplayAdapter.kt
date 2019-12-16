package com.dev.moviedb.mvvm.nowPlayingMoviesTab

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.mvvm.adapters.AbstractMovieItemAdapter
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadPosterUrl
import com.dev.moviedb.mvvm.repository.remote.dto.MovieDTO
import kotlinx.android.synthetic.main.item_movie_cardview_layout.view.*
import petegabriel.com.yamda.R

/**
 * An adapter used to display items with the StaggeredLayoutManager.
 * The listener is used to handle the click event for a given item.
 *
 * Yamda 1.1.0.
 */
//TODO document lambda
class StaggeredMovieDisplayAdapter(var listener: (MovieDTO) -> Unit) : AbstractMovieItemAdapter<StaggeredMovieDisplayAdapter.MovieViewHolder>() {


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies?.get(position), listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
            MovieViewHolder(parent.inflate(R.layout.item_movie_cardview_layout))


    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: MovieDTO?, listener: (MovieDTO) -> Unit) = with(itemView) {
            /*dto from tv shows and imageList use different props for the name
            if (item?.title != null){
                movieTitleTxtView.text = item.title
            }else if (item?.name != null){
                movieTitleTxtView.text = item.name
            }*/

            itemImageFrame.loadPosterUrl(item?.posterPath!!)
            //movieRatingValueTextView.text = "%.1f".format(item.voteAverage)
            itemView.setOnClickListener { listener(item) }
        }
    }

}