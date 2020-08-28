package com.adeona.adeonaadinjectandroidsdk.repo

import android.content.Context
import com.adeona.adeonaadinjectandroidsdk.R
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpace
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.adeona.adeonaadinjectandroidsdk.services.network.api.APIInterface
import com.adeona.adeonaadinjectandroidsdk.services.network.internet.InternetConnection
import com.adeona.adeonaadinjectandroidsdk.services.perfrences.AppPrefs
import com.google.gson.JsonObject

class AdSpaceRepo(private var apiClient: APIInterface, context: Context) {
    private var appPref = AppPrefs
    var mContext = context


    suspend fun getAdSpace(adSpace: AdSpace,type : String): AdSpaceRespond {
        var adSpaceRespond = AdSpaceRespond()
        adSpaceRespond.status = "failed"
        when {
            !InternetConnection.checkInternetConnection(mContext) -> {
                adSpaceRespond.comment = mContext.getString(R.string.no_internet)
                return adSpaceRespond
            }
            adSpace.appId.isNullOrEmpty() -> {
                adSpaceRespond.comment = mContext.getString(R.string.error_empty_appid)
                return adSpaceRespond
            }
            adSpace.adSpaceId.isNullOrEmpty() -> {
                adSpaceRespond.comment = mContext.getString(R.string.error_empty_spaceid)
                return adSpaceRespond
            }
            adSpace.adSpaceType.isNullOrEmpty() -> {
                adSpaceRespond.comment = mContext.getString(R.string.error_empty_spacetype)
                return adSpaceRespond
            }
            type != adSpace.adSpaceType ->{
                adSpaceRespond.comment = mContext.getString(R.string.error_incorrect_space_type)
                return adSpaceRespond
            }

            else -> {
                adSpace.os = mContext.getString(R.string.os)
                adSpace.osVersion = appPref.getOSVersion()
                adSpace.deviceBrand = appPref.getDeviceBrand()
                adSpace.deviceModel = appPref.getDeviceModel()
                adSpace.deviceId = appPref.getSecureId(mContext)
                adSpace.screenWidth = appPref.getScreenWidth(mContext)
                adSpace.screenHeight = appPref.getScreenHeight(mContext)
                adSpace.transactionId = appPref.getTransactionId(adSpace.appId, adSpace.adSpaceId)
                adSpace.appVersion = appPref.getAppVersion(mContext)


                var addSpaceJson = JsonObject()
                addSpaceJson.addProperty("appId", adSpace.appId)
                addSpaceJson.addProperty("adSpaceId", adSpace.adSpaceId)
                addSpaceJson.addProperty("adSpaceType", adSpace.adSpaceType)
                addSpaceJson.addProperty("os", adSpace.os)
                addSpaceJson.addProperty("osVersion", adSpace.osVersion)
                addSpaceJson.addProperty("deviceBrand", adSpace.deviceBrand)
                addSpaceJson.addProperty("deviceModel", adSpace.deviceModel)
                addSpaceJson.addProperty("deviceId", adSpace.deviceId)
                addSpaceJson.addProperty("screenWidth", adSpace.screenWidth)
                addSpaceJson.addProperty("screenHeight", adSpace.screenHeight)
                addSpaceJson.addProperty("transactionId", adSpace.transactionId)
                addSpaceJson.addProperty("vendor", adSpace.vendor)
                addSpaceJson.addProperty("appVersion", adSpace.appVersion)
                addSpaceJson.addProperty("category", adSpace.category)
                addSpaceJson.addProperty("subCategory", adSpace.subCategory)


                adSpaceRespond = apiClient.getAdSpace(addSpaceJson)
                return adSpaceRespond
            }

        }


    }


}