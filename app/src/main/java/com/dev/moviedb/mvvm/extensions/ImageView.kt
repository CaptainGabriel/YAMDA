package com.dev.moviedb.mvvm.extensions

import android.content.Context
import android.widget.ImageView
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import com.squareup.picasso.Picasso
import petegabriel.com.yamda.R

/**
 * This file contains the various extension methods for the class @see(ImageView) to
 * provide a certain utility throughout this application.
 *
 * Yamda 1.0.0.
 */



fun ImageView.loadPosterUrl(url: String, transform: Boolean = true) {
    loadMediumPoster(this, context, url, transform)
}

fun ImageView.loadBackdropUrl(url: String, transform: Boolean = true) {
    loadMediumPoster(this, context, url, transform, ApiConsts.POSTER_BIG_IMG_SIZE)
}

fun ImageView.loadBackdropUrl(url: String, size: String, transform: Boolean = true) {
    loadMediumPoster(this, context, url, transform, size)
}


private fun loadMediumPoster(view: ImageView, context: Context, url: String, transform: Boolean = true, size: String = ApiConsts.POSTER_MEDIUM_IMG_SIZE) {
    var loadUrl = ""
    loadUrl = if (url.isEmpty()) loadUrl else "${ApiConsts.IMG_BASE_URL}$size$url"

    if (transform){
        Picasso.with(context)
                .load(loadUrl)
                .placeholder(R.color.material_grey_300)
                .error(R.color.material_grey_300)
                .transform(RoundedTransformation(7, 4))
                .into(view)
    }else{
        Picasso.with(context)
                .load(loadUrl)
                .placeholder(R.color.material_grey_300)
                .error(R.color.material_grey_300)
                .into(view)
    }
}


fun ImageView.loadRoundedPhoto(context: Context, url: String) {
    var loadUrl = ""//TODO add placeholder
    loadUrl = if (url.isEmpty()) loadUrl else "${ApiConsts.IMG_BASE_URL}${ApiConsts.POSTER_SMALL_IMG_SIZE}$url"

    Picasso.with(context)
        .load(loadUrl)
        .placeholder(R.color.material_grey_300)
        .error(R.color.material_grey_300)
        .transform(RoundedTransformation(7, 4))
        .into(this)
}