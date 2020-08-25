package com.adeona.adeonaadinjectandroidsdk

import android.content.Context
import com.adeona.adeonaadinjectandroidsdk.services.perfrences.AppPrefs

class AdeonaInject {



    fun getID(context: Context) : String{
        return AppPrefs.getSecureId(context)
    }

}