package com.yhc.twtest.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity: RxAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId)
        creatPresenter()
        initData()
    }

    abstract fun initData()

    abstract fun creatPresenter()

    abstract val layoutId:Int
}