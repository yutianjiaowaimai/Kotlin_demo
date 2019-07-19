package com.yhc.twtest.model

import com.yhc.twtest.bean.User
import com.yhc.twtest.contact.MomentContact
import com.yhc.twtest.network.Api
import com.yhc.twtest.network.HostType
import com.yhc.twtest.network.NetworkScheduler
import io.reactivex.Observable
import okhttp3.ResponseBody

class MomentModel: MomentContact.Model {

    //获取朋友圈条目
    override fun getMoments(): Observable<ResponseBody> {
        return Api.instance.getService(HostType.TW).getMoments().compose(NetworkScheduler.compose())
    }

    //获取用户信息
    override fun getUserInfo(): Observable<User> {
        return Api.instance.getService(HostType.TW).getUser().compose(NetworkScheduler.compose())
    }
}