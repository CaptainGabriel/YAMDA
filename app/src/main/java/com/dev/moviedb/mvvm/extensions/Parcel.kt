package com.dev.moviedb.mvvm.extensions

import android.os.Parcel

/**
 * Extension methods for the Parcel type.
 * Yamda 1.0.0.
 */

fun Parcel.writeBoolean(flag: Boolean?) {
    when(flag) {
        true -> writeInt(1)
        false -> writeInt(0)
        else -> writeInt(-1)
    }
}

fun Parcel.readBoolean(): Boolean? {
    return when(readInt()) {
        1 -> true
        0 -> false
        else -> null
    }
}