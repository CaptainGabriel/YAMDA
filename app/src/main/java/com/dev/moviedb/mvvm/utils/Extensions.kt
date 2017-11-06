package com.dev.moviedb.mvvm.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    Picasso.with(context).load("${ApiConsts.IMG_URL_BASE}${ApiConsts.BIG_IMG_SIZE}$url").into(this)
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


private fun StackTraceElement.extractClassName(): String {
    var tag = this.getClassName()
    val m = Estatics.ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return tag.substring(tag.lastIndexOf('.') + 1)
}

