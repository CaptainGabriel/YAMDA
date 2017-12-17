package com.dev.moviedb.mvvm.extensions


/**
 * For a given movie runtime, format it in the style xxhyy where 'xx' is hours
 * and 'yy' is minutes.
 */
fun Int.formatMovieRuntime(): String {
    return if (this > 0) String.format("%d:%d", this / 60, this % 60) else "0:00"
}