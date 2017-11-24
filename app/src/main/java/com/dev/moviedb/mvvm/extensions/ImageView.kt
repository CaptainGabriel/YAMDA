package com.dev.moviedb.mvvm.extensions

import android.widget.ImageView
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import com.squareup.picasso.Picasso

/**
 * This file contains the various extension methods for the class @see(ImageView) to
 * provide a certain utility throughout this application.
 *
 * Yamda 1.0.0.
 */



fun ImageView.loadUrl(url: String, transform: Boolean = true) {
    var loadUrl = ""
    if (url.isEmpty()){
        loadUrl = "https://screenshotlayer.com/images/assets/placeholder.png"
    }else{
        loadUrl = "${ApiConsts.IMG_BASE_URL}${ApiConsts.BIG_IMG_SIZE}$url"
    }
    if (transform){
        Picasso.with(context)
                .load(loadUrl)
                .transform(RoundedTransformation(7, 4))
                .into(this)
    }else{
        Picasso.with(context)
                .load(loadUrl)
                .into(this)
    }
}