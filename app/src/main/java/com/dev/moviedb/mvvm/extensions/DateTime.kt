package com.dev.moviedb.mvvm.extensions

import org.joda.time.DateTime

/**
 *
 * Yamda 1.0.0.
 */


fun DateTime.formatReleaseDate(): String{
    val now = DateTime.now()
    val releaseDate = this

    return if (releaseDate.year < now.year || releaseDate.year > now.year)
        releaseDate.year.toString()
    else
        "" + releaseDate.monthOfYear().get() + "/" + releaseDate.dayOfMonth().get()
}

/**
 * Given two dates, compare both.
 * @return True if date is equal, false otherwise.
 */
fun DateTime.compareToDate(toDate: DateTime): Boolean{
    return this.year == toDate.year &&
            this.monthOfYear == toDate.monthOfYear &&
            this.dayOfMonth == toDate.dayOfMonth
}