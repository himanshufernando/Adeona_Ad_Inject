package com.adeona.adeonaadinjectandroidsdk.addspace

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
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


class Interstitial  @JvmOverloads constructor(context: Context) : Dialog(context) {

    var mContext = context
    var mActivity = context as Activity
    
    private val scope = CoroutineScope(Dispatchers.IO)
    private var adSpaceRepo: AdSpaceRepo = AdSpaceRepo(APIInterface.create(), mContext)

    private var _adSpaceId = ""
    private var _adSpaceType = ""
    private var _appId = ""
    private var _category = ""
    private var _subCategory = ""
    private var _vendor = ""


    var InterstitialDialog: Dialog

    var imgAdspaceBanner : ImageView
    var progressBarBannerLoading : ProgressBar
    var imgAdspaceBannerClose : ImageView
    init {

        InterstitialDialog= Dialog(mContext, R.style.AppTheme)
        InterstitialDialog.setContentView(R.layout.layout_interstitial)

        imgAdspaceBannerClose= InterstitialDialog.findViewById<ImageView>(R.id.img_adspace_inter_close)
        imgAdspaceBanner= InterstitialDialog.findViewById<ImageView>(R.id.img_adspace_inter)
        progressBarBannerLoading= InterstitialDialog.findViewById<ProgressBar>(R.id.progressBar_inter_loading)
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
                adSpaceRepo.getAdSpace(adSpace, "interstitial")
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
                    InterstitialDialog.dismiss()
                }

                InterstitialDialog.show()
            }

        }

    }


    private fun trasperat(activity: Activity) {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            activity.window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            activity.window.statusBarColor = Color.BLACK
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win =mActivity.window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

}