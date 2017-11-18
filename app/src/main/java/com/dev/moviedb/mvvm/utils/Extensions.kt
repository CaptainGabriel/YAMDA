package com.dev.moviedb.mvvm.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dev.moviedb.mvvm.repository.remote.ApiConsts
import com.squareup.picasso.Picasso
import java.util.regex.Pattern



/**
 *
 * This file contains the various extension methods that provide a certain utility through this
 * application.
 *
 * @author PeteGabriel on 27/10/2017
 * Copyright (c) 2017
 * All rights reserved.
 */

object Estatics {

    const val CALL_STACK_INDEX = 1

    val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

}

/**
 *
 * Extension method to help inflate a certain view for a given parent view.
 *
 * Note that @LayoutRes denotes that an integer parameter,
 * field or method return value is expected to be a layout resource reference.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}


fun ImageView.loadUrl(url: String) {
    Picasso.with(context)
            .load("${ApiConsts.IMG_BASE_URL}${ApiConsts.BIG_IMG_SIZE}$url")
            .into(this)
}

/**
 * This function can be used as
 * Log.d("TAG", "message".prependCallLocation())
 */
fun String.prependCallLocation(): String{

    var stackTrace = Throwable().stackTrace
    if (stackTrace.size <= Estatics.CALL_STACK_INDEX){
        throw IllegalStateException(
                "Synthetic stacktrace didn't have enough elements: are you using proguard?")
    }

    val className = stackTrace[Estatics.CALL_STACK_INDEX].extractClassName()
    val lineNumber = stackTrace[Estatics.CALL_STACK_INDEX].lineNumber
    return ".($className.java:$lineNumber) - $this"
}

fun String.formatMovieCardName(): String{
    val maxCharsInMovieName = 17
    if (this.length <= maxCharsInMovieName) return this

    val subVersion = this.substring(0..maxCharsInMovieName)
    if (subVersion[maxCharsInMovieName] ==  ' ' || subVersion.length == maxCharsInMovieName){
        return subVersion
    }else{
        return subVersion.removeRange(maxCharsInMovieName-3, maxCharsInMovieName+1).plus("...")
    }
}


private fun StackTraceElement.extractClassName(): String {
    var tag = this.className
    val m = Estatics.ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return tag.substring(tag.lastIndexOf('.') + 1)
}

