package com.adeona.adeonaadinject

import android.app.Activity
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import com.adeona.adeonaadinjectandroidsdk.addspace.Banner
import com.adeona.adeonaadinjectandroidsdk.addspace.Interstitial
import com.adeona.adeonaadinjectandroidsdk.addspace.PopUp
import com.adeona.adeonaadinjectandroidsdk.addspace.Video
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var inii = Interstitial(this)
        inii.set("appId","adeona22222222")
        inii.set("adSpaceId","tech112345")
        inii.set("adSpaceType","interstitial")
        inii.set("category","sport")
        inii.set("subCategory","cricket")
        inii.showAdSpace()


    }

    private fun trasperat() {
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
           window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.BLACK
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win =window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
}