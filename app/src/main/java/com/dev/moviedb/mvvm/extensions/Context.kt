package com.dev.moviedb.mvvm.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Utility class that provides methods to verify the existence of connectivity.
 *
 * Yamda 1.0.0
 */


/**
 * Get network info.
 *
 * @return An instance of NetworkInfo
 */
fun Context.getNetworkInfo(): NetworkInfo =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

/**
 * Checks if exists an internet connectivity according with the user preferences.
 *
 * @return True if was found a valid connection.
 */
fun Context.isConnected(connType: Long): Boolean {
    if (connType == ConnectivityManager.TYPE_MOBILE.toLong())
        return this.isConnectedToMobile()
    return if (connType == ConnectivityManager.TYPE_WIFI.toLong()) this.isConnectedToWifi() else this.isConnected()
}

/**
 * Check for any connectivity
 *
 * @return True if is connected false otherwise.
 */
fun Context.isConnected(): Boolean = this.getNetworkInfo().isConnected


/**
 * Check for connectivity to a Wifi network
 *
 * @return True if is connected to wifi.
 */
fun Context.isConnectedToWifi(): Boolean {
    val info = this.getNetworkInfo()
    return info.isConnected && info.type == ConnectivityManager.TYPE_WIFI
}

/**
 * Check for any connectivity to a mobile network
 *
 * @return True if is connected to a mobile data connection.
 */
fun Context.isConnectedToMobile(): Boolean {
    val info = this.getNetworkInfo()
    return info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE
}

