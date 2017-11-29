package com.dev.moviedb.mvvm.extensions

import org.joda.time.DateTime

/**
 *
 * Yamda 1.0.0.
 */


fun DateTime.formatReleaseDate(): String{
    val now = DateTime.now()
    val releaseDate = this

    if (releaseDate.year < now.year || releaseDate.year > now.year)
        return releaseDate.year.toString()
    else
        return "" + releaseDate.monthOfYear().get() + "/" + releaseDate.dayOfMonth().get()
}