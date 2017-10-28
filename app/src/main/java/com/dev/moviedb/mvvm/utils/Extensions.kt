package com.dev.moviedb.mvvm.utils

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

    const final val CALL_STACK_INDEX = 1
    final val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
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

