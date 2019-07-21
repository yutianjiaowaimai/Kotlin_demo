package com.yhc.twtest.glide

import android.content.Context
import android.widget.ImageView
import java.lang.ref.SoftReference

class ImageRequest(mContext: Context) {

    //请求地址
    var url = ""

    //图片控件
    var softReferenceView :SoftReference<ImageView>? = null

    //加载中图片id
    var loadingID = 0

    //图片tag
    var tag = ""

    //加载图片回调
    var requestListener:RequestListener? = null

    fun load(url: String): ImageRequest{
        this.url = url
        return this
    }

    fun loading(resId :Int): ImageRequest{
        this.loadingID = resId
        return this
    }

    fun listener(requestListener: RequestListener): ImageRequest{
        this.requestListener = requestListener
        return this
    }

    fun into(img: ImageView){
        img.setTag(this.url)
        this.softReferenceView = SoftReference(img)
        RequestManager.requestManager.addImageRequest(this)
    }

}