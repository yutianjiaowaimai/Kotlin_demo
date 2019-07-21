package com.yhc.twtest.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yhc.twtest.R
import com.yhc.twtest.bean.Images
import com.yhc.twtest.glide.MyGlide
import com.yhc.twtest.utils.SmartUtil

class ImageAdapter(mContext: Context, itemLayoutId: Int, data: List<Images>, val columnCount: Int) : BaseQuickAdapter<Images, BaseViewHolder>(itemLayoutId, data) {

    override fun convert(helper: BaseViewHolder, item: Images) {
        val iv = helper.getView<ImageView>(R.id.iv_image)
        val parameterName = iv.layoutParams.apply {
            when (columnCount) {
                1 -> width = SmartUtil.dp2px(200f)
                else -> width = SmartUtil.getScreenWidth() / 4.7.toInt()
            }
            height = width
        }

        iv.layoutParams = parameterName

        iv.let {

            MyGlide.with(mContext).loading(R.drawable.bg_profile).load(item?.url).listener(
                    object :com.yhc.twtest.glide.RequestListener{
                        override fun onSuccess(bitmap: Bitmap) {
                        }

                        override fun error() {
                            it.setImageResource(R.drawable.bg_profile)
                        }

                    }
            ).into(it)
        }
    }


}