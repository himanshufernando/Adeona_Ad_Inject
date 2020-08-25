package com.adeona.adeonaadinjectandroidsdk.services.perfrences

import android.content.Context
import android.os.Build
import android.provider.Settings

object AppPrefs {

    fun getSecureId(context: Context) :String{
        return  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    fun getOSVersion() :Int{
        return Build.VERSION.SDK_INT
    }

    fun getDeviceModel() :String{
        return Build.MODEL
    }

    fun getDeviceBrand () :String{
        return Build.BRAND
    }

    fun getScreenWidth (context: Context) :Int{
        return context.resources.displayMetrics.widthPixels
    }

    fun getScreenHeight(context: Context) :Int{
        return context.resources.displayMetrics.heightPixels
    }
}