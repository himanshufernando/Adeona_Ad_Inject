package com.adeona.adeonaadinjectandroidsdk.data.model


data class AdSpace(
    var adSpaceId: String,
    var adSpaceType: String,
    var appId: String,
    var appVersion: String,
    var category: String,
    var deviceBrand: String,
    var deviceId: String,
    var deviceModel: String,
    var os: String,
    var osVersion: String,
    var screenHeight: String,
    var screenWidth: String,
    var subCategory: String,
    var transactionId: String,
    var vendor: String
) {
    constructor() : this("","","","","","","","","","","","","","","")
}