package com.yhc.twtest.presenter

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yhc.twtest.bean.Moment
import com.yhc.twtest.bean.User
import com.yhc.twtest.contact.MomentContact
import com.yhc.twtest.model.MomentModel
import com.yhc.twtest.network.NetworkResultDeal
import com.yhc.twtest.network.ResultCallBack
import io.reactivex.functions.Function
import okhttp3.ResponseBody

class MomentPresenter : MomentContact.Presenter {

    //页数索引
    private var pageIndex = 1
    //总页数
    private var pageCount = 0
    //总条目集合
    private var allDataList: List<Moment> = listOf()

    //创建一个handler模拟延时
    private val handler = Handler(Looper.getMainLooper())

    //绑定
    constructor(view: MomentContact.View) {
        this.view = view
        this.model = MomentModel()
    }

    override fun getMoments() {
        model?.getMoments()?.compose(bindUntilOnDestroyEvent())
                ?.map(object : Function<ResponseBody, List<Moment>> {
                    override fun apply(t: ResponseBody): List<Moment> {
                        return Gson().fromJson(t.string(), object : TypeToken<List<Moment>>() {}.type)
                    }
                })?.subscribe(NetworkResultDeal(
                        object : ResultCallBack<List<Moment>> {
                            override fun onSuccess(t: List<Moment>) {
                                //获取朋友圈条目数据成功
                                if (!t.isNullOrEmpty()) {
                                    allDataList = t.filterNot {
                                        it.sender == null && it.content.isNullOrEmpty() && it.images.isNullOrEmpty()
                                    }.apply {
                                        pageCount = Math.ceil((size / 5f).toDouble()).toInt()
                                        view?.showMoment(slice(0..pageIndex * 5 - 1), false)
                                    }
                                }

                            }

                            override fun onFail(failMsg: String) {
                                //获取朋友圈条目数据失败
                                //
                            }

                        }
                ))
    }

    override fun getUserInfo() {
        model?.getUserInfo()?.compose(bindUntilOnDestroyEvent())?.subscribe(NetworkResultDeal(
                object : ResultCallBack<User> {
                    override fun onSuccess(t: User) {
                        //获取用户信息成功
                        view?.showUserInfo(t)
                    }

                    override fun onFail(failMsg: String) {
                        //获取用户信息失败
                    }

                }
        ))
    }

    override fun loadMore() {
        handler.postDelayed(Runnable {

            pageIndex++
            if (pageIndex > pageCount) {
                //没有更多数据
                view?.noMoreData()
            } else if (pageIndex == pageCount) {
                //最后一页
                view?.showMoment(allDataList.slice((pageIndex - 1) * 5..allDataList.size - 1), true)
            } else {
                //其他页
                view?.showMoment(allDataList.slice((pageIndex - 1) * 5..pageIndex * 5 - 1), true)
            }

        },2000)

    }

    override fun refresh() {
        pageIndex = 1
        getMoments()
        getUserInfo()
    }
}