package com.adeona.adeonaadinjectandroidsdk.data.model

data class AdSpaceError(
    var errorStatus: Boolean,
    var errorCode: Int,
    var errorMessage: String
) {
    constructor() : this(true,0,"")
}