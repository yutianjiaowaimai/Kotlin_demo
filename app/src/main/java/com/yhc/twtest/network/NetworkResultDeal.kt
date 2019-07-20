package com.yhc.twtest.network

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class NetworkResultDeal<T>(val resultCallBack: ResultCallBack<T>) : Observer<T> {

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        resultCallBack.onFail(e.toString())
    }

    override fun onNext(t: T) {
        resultCallBack.onSuccess(t)
    }

    override fun onSubscribe(d: Disposable) {

    }
}