package com.adeona.adeonaadinjectandroidsdk.data.model

data class AdSpaceRespond(
    var actionData: ActionData,
    var comment: String,
    var resourceUrl: String,
    var status: String,
    var transactionId: String


){
    constructor() : this(ActionData(),"","","","")


}