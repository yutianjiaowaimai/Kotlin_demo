package com.yhc.twtest.presenter

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yhc.twtest.bean.Moment
import com.yhc.twtest.bean.User
import com.yhc.twtest.contact.MomentContact
import com.yhc.twtest.model.MomentModel
import com.yhc.twtest.network.NetworkResultDeal
import com.yhc.twtest.network.NetworkScheduler
import com.yhc.twtest.network.ResultCallBack
import io.reactivex.functions.Function
import okhttp3.ResponseBody

class MomentPresenter: MomentContact.Presenter{

    //页数索引
    private var pageIndex = 1
    //总页数
    private var pageCount = 0
    //总条目集合
    private var allDataList:List<Moment> = listOf()

    //绑定
    constructor(view:MomentContact.View){
        this.view = view
        this.model = MomentModel()
    }

    override fun getMoments() {
        model?.getMoments()?.compose(bindUntilOnDestroyEvent())
                ?.map(object :Function<ResponseBody,List<Moment>>{
                    override fun apply(t: ResponseBody): List<Moment> {
                        return Gson().fromJson(t.string(),object :TypeToken<List<Moment>>(){}.type)
                    }
                })?.subscribe(NetworkResultDeal(
                        object: ResultCallBack<List<Moment>>{
                            override fun onSuccess(t: List<Moment>) {
                                //获取朋友圈条目数据成功
//                                if(t.()){
//                                    allDataList = t.filterNot {
//                                        it.sender == null && it.content.isNullOrEmpty() && it.images.isNotEmpty()
//                                    }.apply {  }
//                                }
//                                t.slice(0..2)
                                view?.showMoment(t,false)
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
                object: ResultCallBack<User>{
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
        pageIndex ++
        if(pageIndex > pageCount){
            //没有更多数据
        }else if(pageIndex == pageCount){
            //最后一页
        }else{
            //其他页
        }
    }

    override fun refresh() {
        pageIndex = 1
        getMoments()
        getUserInfo()
    }
}