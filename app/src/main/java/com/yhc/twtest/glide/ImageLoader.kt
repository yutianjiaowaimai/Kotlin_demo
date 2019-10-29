package com.yhc.twtest.glide

import android.content.Context
import android.widget.ImageView

class ImageLoader(val mContext: Context) {
    //加载中资源
    var loadingId = 0
    //请求url
    var url = ""
    //目标view
    var targetView: ImageView? = null

    var loadResultListener: LoadResultListener? = null


     fun setLoadingId(resId: Int): ImageLoader{
        this.loadingId   = resId
        return this
    }

    fun setRequestUrl(url :String): ImageLoader{
        this.url = url
        return  this
    }

    fun setTargetView(img: ImageView):ImageLoader{
        this.targetView = img
        return  this
    }

    fun addLoadListener(loadResultListener: LoadResultListener): ImageLoader{
        this.loadResultListener = loadResultListener
        return this
    }

    fun load(){
        targetView?.let {
            MyGlide.with(mContext).loading(loadingId).load(url).into(it)
        }
    }

    fun build(): LoadBuilder{
        return LoadBuilder()
    }

    inner class LoadBuilder{

        fun loadingId(): LoadBuilder{
            return this
        }

        fun url(): LoadBuilder{
            return this
        }

        fun targetView():LoadBuilder{
            return this
        }

    }

}