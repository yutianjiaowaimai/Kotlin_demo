package com.yhc.twtest.network

import com.yhc.twtest.bean.User
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface ApiService {

    //用户信息
    @GET("jsmith")
    fun getUser(): Observable<User>

    //朋友圈条目
    @GET("jsmith/tweets")
    fun getMoments(): Observable<ResponseBody>
}