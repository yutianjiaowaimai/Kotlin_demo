package com.yhc.twtest.glide

import android.content.Context

object MyGlide {

    fun with(context: Context):ImageRequest{
        return ImageRequest(context)
    }
}