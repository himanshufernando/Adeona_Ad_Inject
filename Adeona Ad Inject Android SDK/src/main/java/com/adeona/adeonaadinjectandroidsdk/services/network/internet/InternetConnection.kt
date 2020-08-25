package com.adeona.adeonaadinjectandroidsdk.services.network.internet

import android.content.Context
import android.net.ConnectivityManager

object InternetConnection {

    fun checkInternetConnection(context: Context): Boolean {
        var isConnexted = false
        val connect = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connect.activeNetworkInfo
        if (activeNetwork != null) {
            if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) {
                isConnexted = true
            } else if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) {
                isConnexted = true
            }
        } else {
            isConnexted = false
        }
        return isConnexted
    }

}