package com.adeona.adeonaadinjectandroidsdk.data.model

data class ActionData(
    var imageClick: Boolean,
    var imageClickUrl: String
){
    constructor() : this(false,"")
}