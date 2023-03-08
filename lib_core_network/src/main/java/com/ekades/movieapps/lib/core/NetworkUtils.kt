package com.ekades.movieapps.lib.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.ekades.movieapps.lib.application.ApplicationProvider

object NetworkUtils {

    fun isConnected(): Boolean {
        val cm = ApplicationProvider.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting ?: false
    }
}