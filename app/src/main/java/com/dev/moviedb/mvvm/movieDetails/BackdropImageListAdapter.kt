package com.dev.moviedb.mvvm.movieDetails

import android.os.AsyncTask.execute
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.dev.moviedb.mvvm.extensions.inflate
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import com.dev.moviedb.mvvm.repository.remote.dto.ResultImageDTO
import com.mostafaaryan.transitionalimageview.model.TransitionalImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.backdrop_image_viewholder.view.*
import petegabriel.com.yamda.R
import java.io.IOException


/**
 *
 * Yamda 1.0.0.
 */
class BackdropImageListAdapter(images: Array<ResultImageDTO>) : RecyclerView.Adapter<BackdropImageListAdapter.ImageViewHolder>() {

    private var imageList:  Array<ResultImageDTO> = images

    override fun onBindViewHolder(holder: ImageViewHolder?, position: Int) {
        holder?.bind(imageList[position])
    }

    override fun getItemCount(): Int = imageList.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ImageViewHolder =
            ImageViewHolder(parent?.inflate(R.layout.backdrop_image_viewholder)!!)


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(img: ResultImageDTO) = with(itemView) {
            val url = "${ApiConsts.IMG_BASE_URL}${ApiConsts.POSTER_BIG_IMG_SIZE}${img.filePath}"


            val mainHandler = Handler(context.mainLooper)

            execute {
                try {
                    val bitmap = Picasso.with(context)
                            .load(url)
                            .placeholder(R.color.material_grey_300)
                            .error(R.color.material_grey_300).get()

                    val myRunnable = Runnable { val transitionalImage = TransitionalImage.Builder()
                            .duration(300)
                            .backgroundColor(ContextCompat.getColor(context, android.R.color.white))
                            .image(bitmap)
                            .create()
                        transitional_image.setTransitionalImage(transitionalImage)
                    }

                    mainHandler.post(myRunnable)
                } catch (e: IOException) {
                    Log.e("BackdropImgListAdapter", e.message)
                }
            }

        }
    }

}