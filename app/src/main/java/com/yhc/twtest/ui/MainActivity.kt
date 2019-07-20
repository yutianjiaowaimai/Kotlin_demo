package com.yhc.twtest.ui

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.ButtonBarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.yhc.twtest.R
import com.yhc.twtest.adapter.MomentAdapter
import com.yhc.twtest.base.BaseActivity
import com.yhc.twtest.bean.Moment
import com.yhc.twtest.bean.User
import com.yhc.twtest.contact.MomentContact
import com.yhc.twtest.presenter.MomentPresenter
import com.yhc.twtest.utils.SmartUtil
import com.yhc.twtest.utils.StatusBarUtil

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
    val toolBar: Toolbar by lazy {
        findViewById<Toolbar>(R.id.toolbar)
    }
    val buttonBar: ButtonBarLayout by lazy {
        findViewById<ButtonBarLayout>(R.id.bbl_title)
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

        setStatuBar()
        addScrollListener()
        addRefreshAndLoadmoreListener()
    }

    private fun addRefreshAndLoadmoreListener() {
        refreshLayou.setOnMultiPurposeListener(
                object : SimpleMultiPurposeListener() {

                    override fun onRefresh(refreshLayout: RefreshLayout?) {
                        mPresenter?.refresh()
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout?) {
                        mPresenter?.loadMore()
                    }
                }
        )
    }

    private fun addScrollListener() {
        nestedScrollView.setOnScrollChangeListener(
                object : NestedScrollView.OnScrollChangeListener {
                    private var lastScrollY = 0
                    private val h = SmartUtil.dp2px(176f)
                    private val head = SmartUtil.dp2px(55f)
                    private val color = ContextCompat.getColor(this@MainActivity, R.color.bg_title)
                    override fun onScrollChange(p0: NestedScrollView?, scrollX: Int, scrollY: Int, p3: Int, p4: Int) {
                        //根据滑动状态修改toolbar颜色和透明值
                        if (lastScrollY > h) {
                            buttonBar.alpha = 1f * (scrollY - h) / head
                            toolBar.alpha = 1f * (scrollY - h) / head
                            toolBar.setBackgroundColor((255 * (scrollY - h) / head) shl 24 or color)
                        }

                        lastScrollY = scrollY

                        Log.e("scroll", "height" + scrollY.toString())

                    }

                })

        buttonBar.alpha = 0f
        toolBar.setBackgroundColor(0)
    }

    /**
     * 状态栏透明和间距处理
     */
    private fun setStatuBar() {
        StatusBarUtil.immersive(this)
        StatusBarUtil.setPaddingSmart(this, toolBar)
        StatusBarUtil.setMargin(this, findViewById(R.id.header))
    }

    /**
     * 加载朋友圈条目
     */
    override fun showMoment(list: List<Moment>, isLoadeMore: Boolean) {
        if (isLoadeMore) {
            adapter.addData(list)
            refreshLayou.finishLoadMore()
        } else {
            adapter.setNewData(list)
            refreshLayou.finishRefresh()
        }
    }

    /**
     * 加载用户信息
     */
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
