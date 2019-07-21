package com.yhc.twtest.glide

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.concurrent.LinkedBlockingQueue


class ImageDispatcher(var requestQueue: LinkedBlockingQueue<ImageRequest>): Thread() {

    val handler = Handler(Looper.getMainLooper())
    override fun run() {

        while (!isInterrupted){
            //获取单个图片请求
            val imageRequest = requestQueue.take()
            //展示加载中图片
            showLoadingImg(imageRequest)
            //加载图片
            val bitmap: Bitmap? = loadImg(imageRequest)
            //显示图片
            showImg(imageRequest,bitmap)
        }

    }

    private fun showImg(imageRequest: ImageRequest?,bitmap: Bitmap?) {
        handler.post(Runnable {
            imageRequest?.let {
                if(it.softReferenceView?.get() != null && bitmap != null){
                    it.softReferenceView?.get()!!.apply {
                        if(getTag().equals(it.url)){
                            setImageBitmap(bitmap)
                        }
                    }
                }else{
                    it.requestListener?.error()
                }

            }
        })

    }

    private fun loadImg(imageRequest: ImageRequest?): Bitmap? {

        return downloadImg(imageRequest?.url)

    }

    private fun downloadImg(url: String?): Bitmap? {
        var fos: FileOutputStream? = null
        var inputStream: InputStream? = null
        var bitmap: Bitmap? = null
        try {
            val urlcon: URL = URL(url)
            val openConnection = urlcon.openConnection()
            inputStream = openConnection.getInputStream()
            bitmap = BitmapFactory.decodeStream(inputStream)
        }catch (e: Exception){
            return null
        }finally {
            fos?.close()
            inputStream?.close()
        }

        return bitmap
    }

    private fun showLoadingImg(imageRequest: ImageRequest) {
        imageRequest.apply {
            if(loadingID > 0 && softReferenceView?.get() != null){
                handler.post(Runnable {
                    softReferenceView?.get()!!.setImageResource(loadingID)
                })
            }
        }
    }

}