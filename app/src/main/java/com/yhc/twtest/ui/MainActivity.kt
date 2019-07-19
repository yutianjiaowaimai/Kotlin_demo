package com.yhc.twtest.ui

import android.graphics.drawable.Drawable
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.yhc.twtest.R
import com.yhc.twtest.adapter.MomentAdapter
import com.yhc.twtest.base.BaseActivity
import com.yhc.twtest.bean.Moment
import com.yhc.twtest.bean.User
import com.yhc.twtest.contact.MomentContact
import com.yhc.twtest.presenter.MomentPresenter

class MainActivity : BaseActivity<MomentContact.Model, MomentContact.Presenter>(), MomentContact.View {

    var adapter: MomentAdapter = MomentAdapter(this, R.layout.item_moment, listOf<Moment>())

    val refreshLayou: SmartRefreshLayout by lazy {
        findViewById<SmartRefreshLayout>(R.id.srl)
    }
    val rvMoment: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_moment)
    }
    val nestedScrollView: NestedScrollView by lazy {
        findViewById<NestedScrollView>(R.id.nsv_top)
    }
    val tvUserName: TextView by lazy {
        findViewById<TextView>(R.id.tv_user)
    }
    val ivAvatar: ImageView by lazy {
        findViewById<ImageView>(R.id.iv_user)
    }
    val ivProfile: ImageView by lazy {
        findViewById<ImageView>(R.id.iv_profile)
    }


    override val layoutId: Int = R.layout.activity_main

    override fun creatPresenter(): MomentContact.Presenter = MomentPresenter(this)

    override fun initData() {
        rvMoment.layoutManager = LinearLayoutManager(this)
        rvMoment.adapter = adapter
        mPresenter?.getMoments()
        mPresenter?.getUserInfo()
    }

    //加载朋友圈条目
    override fun showMoment(list: List<Moment>, isLoadeMore: Boolean) {
        if (isLoadeMore) {
            adapter.addData(list)
            refreshLayou.finishLoadMore()
        }else{
            adapter.setNewData(list)
            refreshLayou.finishRefresh()
        }
    }

    //加载用户信息
    override fun showUserInfo(user: User) {
        tvUserName.text = user?.nick
        Glide.with(this).load(user?.avatar).into(ivAvatar)
        Glide.with(this).load(user?.profile_image).addListener(
                object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        ivProfile.setImageResource(R.drawable.bg_profile)
                        return true
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                }
        ).into(ivProfile)

    }

    //没有更的数据了
    override fun noMoreData() {
        refreshLayou.finishLoadMoreWithNoMoreData()
    }
}
