package com.adeona.adeonaadinjectandroidsdk.addspace

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.adeona.adeonaadinjectandroidsdk.R
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpace
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.adeona.adeonaadinjectandroidsdk.repo.AdSpaceRepo
import com.adeona.adeonaadinjectandroidsdk.services.network.api.APIInterface
import com.bumptech.glide.Glide

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PopUp @JvmOverloads constructor(context: Context) : Dialog(context) {

    private var mContext = context
    var mActivity = context as Activity
    private val scope = CoroutineScope(Dispatchers.IO)

    private var adSpaceRepo: AdSpaceRepo = AdSpaceRepo(APIInterface.create(), mContext)

    private var _adSpaceId = ""
    private var _adSpaceType = ""
    private var _appId = ""
    private var _category = ""
    private var _subCategory = ""
    private var _vendor = ""

     var popUpAdd: Dialog
    var imgAdspaceBanner : ImageView
     var progressBarBannerLoading : ProgressBar
    var imgAdspaceBannerClose : ImageView

   init {

       popUpAdd = Dialog(mContext)
       popUpAdd.requestWindowFeature(Window.FEATURE_NO_TITLE)
       popUpAdd.window!!.setBackgroundDrawableResource(android.R.color.transparent)
       popUpAdd.setContentView(R.layout.layout_popup)
       popUpAdd.setCancelable(false)

       imgAdspaceBannerClose= popUpAdd.findViewById<ImageView>(R.id.img_adspace_banner)
       imgAdspaceBanner= popUpAdd.findViewById<ImageView>(R.id.img_adspace_banner)
       progressBarBannerLoading= popUpAdd.findViewById<ProgressBar>(R.id.progressBar_banner_loading)

   }



    fun set(key: String, value: String) {
        when (key) {
            "appId" -> _appId = value
            "adSpaceId" -> _adSpaceId = value
            "adSpaceType" -> _adSpaceType = value
            "category" -> _category = value
            "subCategory" -> _subCategory = value
            "vendor" -> _vendor = value
        }
    }


    fun showAdSpace() {
        var adSpace = AdSpace().apply {
            appId = _appId
            adSpaceId = _adSpaceId
            adSpaceType = _adSpaceType
            category = _category
            subCategory = _subCategory
            vendor = _vendor
        }
        callAdspace(adSpace)

    }

    private fun callAdspace(adSpace: AdSpace) = scope.launch {
        try {
            val adSpaceRespond: AdSpaceRespond = withContext(Dispatchers.Main) {
                adSpaceRepo.getAdSpace(adSpace, "popUp")
            }
            setAdSpaceData(adSpaceRespond)
        } catch (ex: Exception) {
            Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun setAdSpaceData(adSpaceRespond: AdSpaceRespond) {
        mActivity?.runOnUiThread {
            progressBarBannerLoading.visibility = View.GONE
            if (adSpaceRespond.status == "failed") {
                Toast.makeText(mContext, adSpaceRespond.comment, Toast.LENGTH_LONG).show()
            } else {

                Glide.with(mContext)
                    .load(adSpaceRespond.resourceUrl)
                    .fitCenter()
                    .into(imgAdspaceBanner)
                if(adSpaceRespond.actionData.imageClick){
                    imgAdspaceBanner.setOnClickListener {
                        val url = adSpaceRespond.actionData.imageClickUrl
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        mActivity.startActivity(i)
                    }
                }else{
                }


                imgAdspaceBannerClose.setOnClickListener {
                    popUpAdd.dismiss()
                }

                popUpAdd.show()
            }

        }


    }

}