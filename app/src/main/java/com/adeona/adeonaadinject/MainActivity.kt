package com.adeona.adeonaadinject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.adeona.adeonaadinjectandroidsdk.addspace.Banner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



       var nann =Banner(this)

        nann.set("appId","adeona22222222")
        nann.set("adSpaceId","tech112345")
        nann.set("adSpaceType","banner")
        nann.showAdSpace()
//

    }
}