package com.yhc.twtest.contact

import com.yhc.twtest.base.BaseModel
import com.yhc.twtest.base.BasePresenter
import com.yhc.twtest.base.BaseView
import com.yhc.twtest.bean.Moment
import com.yhc.twtest.bean.User
import io.reactivex.Observable
import okhttp3.ResponseBody

interface MomentContact {

    interface View : BaseView {

        fun showMoment(list: List<Moment>, isLoadeMore: Boolean)

        fun showUserInfo(user: User)

        fun noMoreData()
    }

    interface Model : BaseModel {

        fun getMoments(): Observable<ResponseBody>

        fun getUserInfo(): Observable<User>
    }

    abstract class Presenter : BasePresenter<View, Model>() {

        abstract fun getMoments()

        abstract fun getUserInfo()

        abstract fun loadMore()

        abstract fun refresh()
    }
}