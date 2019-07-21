package com.yhc.twtest.glide

import android.graphics.Bitmap

interface RequestListener {
    fun onSuccess(bitmap: Bitmap)
    fun error()
}