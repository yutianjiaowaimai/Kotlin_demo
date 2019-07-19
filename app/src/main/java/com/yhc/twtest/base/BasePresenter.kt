package com.yhc.twtest.base

import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragment

abstract class BasePresenter<V,M>: IPresenter<M> {

    var view: V? = null
    var model: M? = null

    fun <VT> bindUntilOnDestroyEvent(): LifecycleTransformer<VT> {
        if (view is RxFragment) {
            return bindFragmentUntilEvent(FragmentEvent.DESTROY)
        }
        return bindActivityUntilEvent(ActivityEvent.DESTROY)
    }

    /**
     * 绑定 RxActivity 指定生命周期
     */
    fun <VT> bindActivityUntilEvent(event: ActivityEvent): LifecycleTransformer<VT> {
        return (view as RxAppCompatActivity).bindUntilEvent(event)
    }

    /**
     * 绑定 RxFragment 指定的声明周期
     */
    fun <VT> bindFragmentUntilEvent(event: FragmentEvent): LifecycleTransformer<VT> {
        return (view as RxFragment).bindUntilEvent(event)
    }
}