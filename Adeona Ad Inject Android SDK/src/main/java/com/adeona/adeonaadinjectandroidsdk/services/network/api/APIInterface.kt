package com.adeona.adeonaadinjectandroidsdk.services.network.api

import com.adeona.adeonaadinjectandroidsdk.BuildConfig
import com.adeona.adeonaadinjectandroidsdk.data.model.AdSpaceRespond
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.internal.http2.Http2Reader.Companion.logger
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException
import java.util.concurrent.TimeUnit

interface APIInterface {

    @POST("api/adRequest")
    suspend fun getAdSpace(@Body adSpaceInfo: JsonObject): AdSpaceRespond


    companion object {

        fun create(): APIInterface = create(BuildConfig.API_BASE_URL.toHttpUrlOrNull()!!)
        fun create(httpUrl: HttpUrl): APIInterface {
            val client = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            return Retrofit.Builder()
                .baseUrl(httpUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIInterface::class.java)


        }
    }

}