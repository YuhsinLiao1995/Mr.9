package com.tina.mr9.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.tina.mr9.Mr9Application


object Util {

    /**
     * Determine and monitor the connectivity status
     *
     * https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
     */
    fun isInternetConnected(): Boolean {
        val cm = Mr9Application.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return Mr9Application.instance.getString(resourceId)
    }

    fun getColor(resourceId: Int): Int {
        return Mr9Application.instance.getColor(resourceId)
    }
}
