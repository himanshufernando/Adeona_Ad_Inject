package com.adeona.adeonaadinjectandroidsdk.services.perfrences

import android.content.Context
import android.content.pm.PackageInfo
import android.os.Build
import android.provider.Settings

import java.util.*


object AppPrefs {

    enum class adSpaceType {
        banner, popUp, native, video, interstitial
    }


    // function to get device Id
    fun getSecureId(context: Context) :String{
        return  Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    // function to get OS Version
    fun getOSVersion() :String{
        return Build.VERSION.SDK_INT.toString()
    }
    // function to get Device Model
    fun getDeviceModel() :String{
        return Build.MODEL
    }
    // function to get Device Brand
    fun getDeviceBrand () :String{
        return Build.BRAND
    }
    // function to get Device Screen Width
    fun getScreenWidth(context: Context) :String{
        return context.resources.displayMetrics.widthPixels.toString()
    }
    // function to get Device Screen Height
    fun getScreenHeight(context: Context) :String{
        return context.resources.displayMetrics.heightPixels.toString()
    }

    // function to generate unique id for Transaction
    fun getTransactionId(appid: String, adSpaceid: String) :String{
        val c: Calendar = Calendar.getInstance()
        var numberFromTime =
            c.get(Calendar.MONTH).toString() +
                    c.get(Calendar.DATE).toString() +
                    c.get(Calendar.YEAR).toString() +
                    c.get(Calendar.HOUR).toString() +
                    c.get(Calendar.MINUTE).toString() +
                    c.get(Calendar.SECOND).toString() +
                    c.get(Calendar.MILLISECOND).toString()+((1..1000).random()).toString()
        return appid+adSpaceid+numberFromTime

    }

    // function to get app version
    fun getAppVersion(context: Context) :String{
        val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        return packageInfo.versionName
    }


}