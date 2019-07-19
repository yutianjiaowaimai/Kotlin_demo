package com.yhc.twtest.network

interface ResultCallBack<T> {

    fun onSuccess(t: T)

    fun onFail(failMsg: String)
}