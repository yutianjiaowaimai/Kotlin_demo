package com.example.mylibrary

import android.content.Context

object MyGlide {

    fun with(context: Context):ImageRequest{
        return ImageRequest(context)
    }
}