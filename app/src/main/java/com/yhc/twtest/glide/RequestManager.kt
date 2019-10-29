package com.yhc.twtest.glide

import java.util.concurrent.LinkedBlockingQueue

class RequestManager {

    constructor(){
        start()
    }

    companion object{
        val requestManager = RequestManager()
    }
    var imageDispatcher: MutableList<ImageDispatcher> = mutableListOf()
    var requestQueue: LinkedBlockingQueue<ImageRequest> = LinkedBlockingQueue()
    //执行县城任务
    fun start(){
        //开始前停止之前的工作
        stop()
        //当前手机最大工作线程数
        val availableProcessors = Runtime.getRuntime().availableProcessors()
        for(index in 1..availableProcessors){

            val dispatcher = ImageDispatcher(requestQueue)
            dispatcher.start()
            imageDispatcher.add(dispatcher)

        }
    }

    private fun stop() {
        //如果还有任务，就停止
        if(!imageDispatcher .isNullOrEmpty()){
            imageDispatcher.forEach{
                if(!it.isInterrupted){
                    it.interrupt()
                }
            }
        }

    }

    fun addImageRequest(imageRequest: ImageRequest){
        imageRequest?.let {
            if (it !in requestQueue) {
                requestQueue.add(it)
            }
        }
        //start()
    }


}