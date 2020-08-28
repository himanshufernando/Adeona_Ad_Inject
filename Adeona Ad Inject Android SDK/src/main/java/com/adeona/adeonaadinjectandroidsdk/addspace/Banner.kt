package com.adeona.adeonaadinjectandroidsdk.addspace


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.*
import com.adeona.adeonaadinjectandroidsdk.R
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpace
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.adeona.adeonaadinjectandroidsdk.repo.AdSpaceRepo
import com.adeona.adeonaadinjectandroidsdk.services.network.api.APIInterface
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_banner.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    private var mContext = context

    private val scope = CoroutineScope(Dispatchers.IO)
    private var adSpaceRepo: AdSpaceRepo = AdSpaceRepo(APIInterface.create(), mContext)
    private var _adSpaceId = ""
    private var _adSpaceType = ""
    private var _appId = ""
    private var _category = ""
    private var _subCategory = ""
    private var _vendor = ""

    private  var mActivity = context as Activity

    init {
        inflate(context, R.layout.layout_banner, this)
        var  attributes = context.obtainStyledAttributes(attrs, R.styleable.BannerView)
        ll_banner_main.visibility = View.GONE

        img_adspace_banner_close.setOnClickListener {
            ll_banner_main.visibility = View.GONE
        }
        attributes.recycle()

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
                adSpaceRepo.getAdSpace(adSpace,"banner")
            }
            setAdSpaceData(adSpaceRespond)
        } catch (ex: Exception) {
            Toast.makeText(mContext, ex.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun setAdSpaceData(adSpaceRespond: AdSpaceRespond) {
        var imgAdspaceBanner= mActivity.findViewById<ImageView>(R.id.img_adspace_banner)
        var progressBarBannerLoading= mActivity.findViewById<ProgressBar>(R.id.progressBar_banner_loading)
        var llBannerMain= mActivity.findViewById<LinearLayout>(R.id.ll_banner_main)
        mActivity?.runOnUiThread {
            progressBarBannerLoading.visibility = View.GONE
            if (adSpaceRespond.status == "failed") {
                Toast.makeText(mContext, adSpaceRespond.comment, Toast.LENGTH_SHORT).show()
            } else {
                llBannerMain.visibility = View.VISIBLE
                imgAdspaceBanner.visibility = View.VISIBLE
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


            }

        }

    }
}