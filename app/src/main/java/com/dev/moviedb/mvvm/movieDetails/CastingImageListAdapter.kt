package com.dev.moviedb.mvvm.movieDetails

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.extensions.loadRoundedPhoto
import com.dev.moviedb.mvvm.repository.remote.dto.CastDTO
import kotlinx.android.synthetic.main.image_movie_viewholder.view.*
import petegabriel.com.yamda.R

/**
 * Adapter class used to display a list of images inside the details view of a certain movie.
 *
 * Yamda 1.1.0.
 */
class CastingImageListAdapter(images: Array<CastDTO>) : RecyclerView.Adapter<CastingImageListAdapter.ImageViewHolder>() {

    private var imageList:  Array<CastDTO> = images

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder(parent.inflate(R.layout.image_movie_viewholder))


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cast: CastDTO) = with(itemView) {
            cast.profile_path?.let { casting_crew_photo.loadRoundedPhoto(itemView.context, it) }
        }
    }

}