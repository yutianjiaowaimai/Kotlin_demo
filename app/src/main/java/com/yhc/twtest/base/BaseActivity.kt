package com.yhc.twtest.base

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

abstract class BaseActivity<M : BaseModel, P : IPresenter<M>> : RxAppCompatActivity() {

    protected var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutId)
        mPresenter = creatPresenter()
        initData()
    }

    abstract fun initData()

    abstract fun creatPresenter(): P

    abstract val layoutId: Int
}