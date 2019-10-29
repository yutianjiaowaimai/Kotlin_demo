package com.yhc.twtest.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    companion object {
        val instance by lazy { Api() }
        const val READ_TIME_OUT = 5000
        const val CONNECT_TIME_OUT = 5000
    }

    //初始化okhttp
    val okHttpClient: OkHttpClient
        get() {
            val logInterceptor = HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val build = OkHttpClient.Builder()
                    .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                    .connectTimeout(CONNECT_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                    .addInterceptor(logInterceptor)
                    .build()

            return build
        }


    fun getService(hostType: HostType): ApiService {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create()

        //初始化retrofit
        val retrofit: Retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiConstant.getHost(hostType))
                .build()

        //创建ApiService
        return retrofit.create(ApiService::class.java)
    }
}