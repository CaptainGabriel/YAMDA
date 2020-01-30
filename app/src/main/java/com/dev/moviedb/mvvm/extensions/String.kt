package com.dev.moviedb.mvvm.extensions

import org.joda.time.DateTime
import java.text.DateFormat
import java.util.regex.Pattern

/**
 * This file contains the various extension methods for the class @see(String) to
 * provide a certain utility throughout this application.
 *
 * Yamda 1.1.0.
 */

/**
 * This function can be used as
 * Log.d("TAG", "message".prependCallLocation())
 */
fun String.prependCallLocation(): String{

    val stackTrace = Throwable().stackTrace
    if (stackTrace.size <= EnvVars.CALL_STACK_INDEX){
        throw IllegalStateException(
                "Synthetic stacktrace didn't have enough elements: are you using proguard?")
    }

    val className = stackTrace[EnvVars.CALL_STACK_INDEX].extractClassName()
    val lineNumber = stackTrace[EnvVars.CALL_STACK_INDEX].lineNumber
    return ".($className.kt:$lineNumber) - $this"
}

/**
 * Use this method to format movie names when showing them in cardviews or something.
 */
fun String.formatMovieCardName(maxCharsInMovieName : Int = 17): String{
    if (this.length <= maxCharsInMovieName) return this

    val subVersion = this.substring(0..maxCharsInMovieName)
    if (subVersion[maxCharsInMovieName] ==  ' ') {
        return subVersion.removeSuffix(" ").plus("...")
    }else if (subVersion.length == maxCharsInMovieName){
        return subVersion
    }else{
        return subVersion.removeRange(maxCharsInMovieName-3, maxCharsInMovieName+1).plus("...")
    }
}


/**
 * Helper method that based upon a string that represents
 * a valid date in the format "yyyy-mm-dd" returns the year or
 * the acronym "TBA" if the parameter is invalid.
 *
 * "Low level" implementation because we cannot call buildDate or will overflow the call stack.
 *
 * @param dateStr valid date in the format "yyyy-mm-dd"
 * @return the year
 */
fun String.getYear(): String {
    return if (this.isEmpty() || !hasValidFormat(this))
        EnvVars.TO_BE_ANNOUNCED
    else
        this.substring(0, this.indexOf('-'))
}

fun String.getMonth(): String {
    return if (this.isEmpty() || !hasValidFormat(this)) "0" else this.substring(5, 7)
}


fun String.getDay(): String {
    return if (this.isEmpty() || !hasValidFormat(this)) "0" else this.substring(8)
}

/**
 * Helper method that based upon a string that represents
 * a valid date in the format "yyyy-mm-dd" returns the month/day or
 * the acronym "TBA" if the parameter is invalid.
 *
 *
 */
fun String.getMonthAndDay(): String {
    return if (this.isEmpty() || !hasValidFormat(this))
        EnvVars.TO_BE_ANNOUNCED
    else
        this.getMonth() + "/" + this.getDay()
}

fun String.getExtendedDate(): String{
    return DateFormat.getDateInstance(DateFormat.LONG).format(this.toDatetime()?.toDate())
}


/**
 * Builds an instance of DateTime from the given string if one follows the
 * pattern "yyyy-mm-dd". Otherwise return null;
 *
 */
fun String.toDatetime(): DateTime? {
    return try {
        val year = Integer.valueOf(this.getYear())
        val m = Integer.valueOf(this.getMonth())
        val d = Integer.valueOf(this.getDay())
        DateTime(year, m, d, 0, 0)
    } catch (ex: NumberFormatException) {
        null
    }
}


/**
 * Checks if a given date is of today's day.
 * @return True if date is equal to today's date, false otherwise.
 */
fun String.isToday(): Boolean {
    val release = this.toDatetime() ?: return false
    return release.compareToDate(DateTime.now())
}

/**
 * Checks if a given date is of tomorrow's day.
 * @return True if date is equal to tomorrow's date, false otherwise.
 */
fun String.isTomorrow(): Boolean {
    val release = this.toDatetime() ?: return false
    return release.compareToDate(DateTime.now().plusDays(1))
}



/**
 * Checks if the given date follows the pattern "yyyy-mm-dd".
 */
private fun hasValidFormat(dateStr: String?): Boolean {
    if (dateStr == null) return false
    val matcher = Pattern.compile("\\d{4}\\-\\d{2}\\-\\d{2}").matcher(dateStr)
    return matcher.matches()
}


private fun StackTraceElement.extractClassName(): String {
    var tag = this.className
    val m = EnvVars.ANONYMOUS_CLASS.matcher(tag)
    if (m.find()) {
        tag = m.replaceAll("")
    }
    return tag.substring(tag.lastIndexOf('.') + 1)
}

object EnvVars {
    const val CALL_STACK_INDEX = 1

    val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")!!

    const val TO_BE_ANNOUNCED = "TBA"

}