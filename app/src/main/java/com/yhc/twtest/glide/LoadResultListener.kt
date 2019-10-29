package com.yhc.twtest.glide

import android.graphics.Bitmap

/**
 * 加载图片结果监听
 */
interface LoadResultListener {

    fun onSuccess(bitmap: Bitmap)

    fun onFail()
}