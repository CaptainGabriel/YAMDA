package com.dev.moviedb.mvvm.extensions

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



fun ImageView.loadUrl(url: String, transform: Boolean = true) {
    var loadUrl = ""
    loadUrl = if (url.isEmpty()) loadUrl else "${ApiConsts.IMG_BASE_URL}${ApiConsts.MEDIUM_IMG_SIZE}$url"

    if (transform){
        Picasso.with(context)
                .load(loadUrl)
                .placeholder(R.color.material_grey_300)
                .error(R.color.material_grey_300)
                .transform(RoundedTransformation(7, 4))
                .into(this)
    }else{
        Picasso.with(context)
                .load(loadUrl)
                .placeholder(R.color.material_grey_300)
                .error(R.color.material_grey_300)
                .into(this)
    }
}