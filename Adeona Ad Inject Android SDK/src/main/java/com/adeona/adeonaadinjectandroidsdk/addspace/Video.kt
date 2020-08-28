package com.adeona.adeonaadinjectandroidsdk.addspace

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.VideoView
import com.adeona.adeonaadinjectandroidsdk.R
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpace
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.adeona.adeonaadinjectandroidsdk.repo.AdSpaceRepo
import com.adeona.adeonaadinjectandroidsdk.services.network.api.APIInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Video @JvmOverloads constructor(context: Context) : Dialog(context) {

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

     var videoAdd: Dialog

    var imgAdspaceBannerClose : ImageView
    var vidAdspaceBanner : VideoView
    var progressBarBannerLoading : ProgressBar
    var imgAdspaceVideoPlay : ImageView
   init {

       videoAdd = Dialog(mContext)
       videoAdd.requestWindowFeature(Window.FEATURE_NO_TITLE)
       videoAdd.window!!.setBackgroundDrawableResource(android.R.color.transparent)
       videoAdd.setContentView(R.layout.layout_video)
       videoAdd.setCancelable(false)

       imgAdspaceBannerClose= videoAdd.findViewById<ImageView>(R.id.img_adspace_video_close)
       vidAdspaceBanner =  videoAdd.findViewById<VideoView>(R.id.vid_adspace_banner)
       progressBarBannerLoading= videoAdd.findViewById<ProgressBar>(R.id.progressBar_banner_loading)

       imgAdspaceVideoPlay=  videoAdd.findViewById<ImageView>(R.id.img_adspace_video_play)
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
                adSpaceRepo.getAdSpace(adSpace, "video")
            }
            setAdSpaceData(adSpaceRespond)
        } catch (ex: Exception) {
            Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun setAdSpaceData(adSpaceRespond: AdSpaceRespond) {
        mActivity?.runOnUiThread {
            if (adSpaceRespond.status == "failed") {
                Toast.makeText(mContext, adSpaceRespond.comment, Toast.LENGTH_LONG).show()
            } else {

                val vidUri = Uri.parse(adSpaceRespond.resourceUrl)

                val metrics = DisplayMetrics()
                mActivity.windowManager.defaultDisplay.getMetrics(metrics)
                val params = vidAdspaceBanner.layoutParams
                params.width = metrics.widthPixels
                params.height = 350
                vidAdspaceBanner.layoutParams = params



                imgAdspaceVideoPlay.setOnClickListener {
                    progressBarBannerLoading.visibility =View.VISIBLE
                    imgAdspaceVideoPlay.visibility =View.GONE
                    vidAdspaceBanner.setVideoURI(vidUri)
                    vidAdspaceBanner.start()

                }


                if(adSpaceRespond.actionData.imageClick){
                    vidAdspaceBanner.setOnClickListener {
                        val url = adSpaceRespond.actionData.imageClickUrl
                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        mActivity.startActivity(i)
                    }
                }else{
                }


                imgAdspaceBannerClose.setOnClickListener {
                    videoAdd.dismiss()
                }

                vidAdspaceBanner.setOnPreparedListener {
                    progressBarBannerLoading.visibility =View.GONE
                }

                videoAdd.show()
            }

        }


    }



}