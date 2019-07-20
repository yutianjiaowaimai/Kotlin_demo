package com.yhc.twtest.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yhc.twtest.R
import com.yhc.twtest.bean.Moment

class MomentAdapter(mContext: Context, itemLayoutId: Int, data: List<Moment>) : BaseQuickAdapter<Moment, BaseViewHolder>(itemLayoutId, data) {

    override fun convert(helper: BaseViewHolder, item: Moment) {

        helper.apply {
            getView<ImageView>(R.id.iv_sender).let {
                Glide.with(mContext).load(item.sender?.avatar).addListener(
                        object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                it.setImageResource(R.drawable.bg_profile)
                                return true
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                return false
                            }

                        }
                ).into(it)
            }

            getView<RecyclerView>(R.id.rv_image).apply {
                if (!item.images.isNullOrEmpty()) {
                    var columnCount = 1
                    when (item.images?.size) {
                        1 -> columnCount = 1
                        2 -> columnCount = 2
                        else -> columnCount = 3
                    }

                    layoutManager = GridLayoutManager(mContext, columnCount)
                    adapter = ImageAdapter(mContext, R.layout.item_image, item.images, columnCount)
                }
            }

            getView<RecyclerView>(R.id.rv_comments).apply {
                if (!item.comments.isNullOrEmpty()) {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = CommentAdapter(R.layout.item_comment, item.comments)
                }

            }

            setText(R.id.tv_sender_name, item.sender?.nick)
            setText(R.id.tv_content, item.content)

        }


    }
}