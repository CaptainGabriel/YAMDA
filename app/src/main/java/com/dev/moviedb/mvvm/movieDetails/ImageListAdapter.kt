package com.dev.moviedb.mvvm.movieDetails

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadBackdropUrl
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import petegabriel.com.yamda.R

/**
 * Adapter class used to display a list of images inside the details view of a certain movie.
 *
 * Yamda 1.0.0.
 */
class ImageListAdapter: RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    var imageList: ArrayList<String>? = null

    fun addNewData(movies: ArrayList<String>?){
        this.imageList = movies
    }

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        holder?.bind(imageList?.get(position)!!)
    }

    override fun getItemCount(): Int = if (imageList != null) imageList!!.size else 0

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder =
        ImageViewHolder(parent?.inflate(R.layout.image_movie_viewholder)!!)


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(uri: String) = with(itemView) {
            (itemView as ImageView).loadBackdropUrl(uri, ApiConsts.POSTER_SMALL_IMG_SIZE)
        }
    }

}