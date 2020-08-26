package com.adeona.adeonaadinjectandroidsdk.addspace


import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import com.adeona.adeonaadinjectandroidsdk.R
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpace
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.adeona.adeonaadinjectandroidsdk.repo.AdSpaceRepo
import com.adeona.adeonaadinjectandroidsdk.services.network.api.APIInterface
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class Banner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


     var att  = attrs

    // private  var mActivity = activity
    private var mContext = context
    var activity = context as Activity
    private val scope = CoroutineScope(Dispatchers.IO)

    private var adSpaceRepo: AdSpaceRepo = AdSpaceRepo(APIInterface.create(), mContext)

    private var _adSpaceId = ""
    private var _adSpaceType = ""
    private var _appId = ""
    private var _category = ""
    private var _subCategory = ""
    private var _vendor = ""


    private var xxxx = ""

    // layout variables
    lateinit var progressBar: ProgressBar
    lateinit var imageViewBanner: ImageView
    lateinit var linearLayoutMain: LinearLayout
    lateinit var imageViewClose: ImageView





    init {
        inflate(context, R.layout.layout_banner, this)

        imageViewClose = findViewById(R.id.img_adspace_banner_close)
        linearLayoutMain = findViewById(R.id.ll_banner_main)
        imageViewBanner = findViewById(R.id.img_adspace_banner)
        progressBar = findViewById(R.id.progressBar)
        att = attrs
        initView(att)
    }


    private fun initView(attrs: AttributeSet?) {
        attrs ?: return

        val attributeValues = context.obtainStyledAttributes(attrs, R.styleable.BannerView)
        with(attributeValues) {

            recycle()
        }
    }



/*
    init {



        inflate(context, R.layout.layout_banner, this)


        // initialize layout variables
        imageViewClose = findViewById(R.id.img_adspace_banner_close)
        linearLayoutMain = findViewById(R.id.ll_banner_main)
        imageViewBanner = findViewById(R.id.img_adspace_banner)
        progressBar = findViewById(R.id.progressBar)

        attributes = context.obtainStyledAttributes(attrs, R.styleable.BannerView)




        progressBar.visibility = View.GONE



        // cloase image onclick
        imageViewClose.setOnClickListener {
            linearLayoutMain.visibility = View.GONE

        }


         attributes.recycle()
    }
*/




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
          // var respons = adSpaceRepo.getAdSpace(adSpace)
          //  setAdSpaceData(respons)
          //  println("aaaaaaaaaaaaaaaaaaaaa respons : " + respons)

            val movies: AdSpaceRespond = async(Dispatchers.Main) {
                adSpaceRepo.getAdSpace(adSpace)
            }.await()

           setAdSpaceData(movies)

        } catch (ex: Exception) {
            println("aaaaaaaaaaaaaaaaaaaaa Exception: " + ex)
        }

    }
    private fun setAdSpaceData(adSpaceRespond: AdSpaceRespond){

        println("aaaaaaaaaaaaaaaaaaaaa adSpaceRespond: " + adSpaceRespond)


        activity?.runOnUiThread(java.lang.Runnable {
            Glide.with(mContext)
                .load("https://smartmessenger.lk/Uploads/iap/banner_1.jpg")
                .centerCrop()
                .into(imageViewBanner)
        })



        initView(att)
/*
        xxxx = adSpaceRespond.resourceUrl


        Glide.with(mContext)
            .load(xxxx)
            .centerCrop()
            .into(imageViewBanner)

        attributes.recycle()*/


     /*   Glide.with(mContext)
            .load("https://smartmessenger.lk/Uploads/iap/banner_1.jpg")
            .centerCrop()
            .into(imageViewBanner)*/

      //  imageViewBanner.setImageResource(R.drawable.ic_add_play)

      //  attributes.recycle()
    }
}