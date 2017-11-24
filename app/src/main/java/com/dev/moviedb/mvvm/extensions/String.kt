package com.dev.moviedb.mvvm.extensions

import java.util.regex.Pattern

/**
 * This file contains the various extension methods for the class @see(String) to
 * provide a certain utility throughout this application.
 *
 * Yamda 1.0.0.
 */

/**
 * This function can be used as
 * Log.d("TAG", "message".prependCallLocation())
 */
fun String.prependCallLocation(): String{

    var stackTrace = Throwable().stackTrace
    if (stackTrace.size <= EnvVars.CALL_STACK_INDEX){
        throw IllegalStateException(
                "Synthetic stacktrace didn't have enough elements: are you using proguard?")
    }

    val className = stackTrace[EnvVars.CALL_STACK_INDEX].extractClassName()
    val lineNumber = stackTrace[EnvVars.CALL_STACK_INDEX].lineNumber
    return ".($className.java:$lineNumber) - $this"
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

    val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

}