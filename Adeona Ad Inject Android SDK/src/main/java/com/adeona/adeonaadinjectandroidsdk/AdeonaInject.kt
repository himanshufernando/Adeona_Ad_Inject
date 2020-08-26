package com.adeona.adeonaadinjectandroidsdk

import android.content.Context
import com.adeona.adeonaadinjectandroidsdk.services.perfrences.AppPrefs

class AdeonaInject(context: Context) {

    var mContext = context





    fun getID() : String{
        return AppPrefs.getSecureId(mContext)
    }

}