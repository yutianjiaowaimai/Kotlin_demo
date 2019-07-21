package com.example.mylibrary

import android.graphics.Bitmap

interface RequestListener {
    fun onSuccess(bitmap: Bitmap)
    fun error()
}