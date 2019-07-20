package com.yhc.twtest.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yhc.twtest.R
import com.yhc.twtest.bean.Comments

class CommentAdapter(itemLayoutId: Int, data: List<Comments>) : BaseQuickAdapter<Comments, BaseViewHolder>(itemLayoutId, data) {

    override fun convert(helper: BaseViewHolder, item: Comments) {
        val colorIndex = item.sender?.nick.length
        helper.setText(R.id.tv_comment, SpannableString(item.sender?.nick + "：" + item.content)
                .apply {
                    setSpan(ForegroundColorSpan(Color.parseColor("#586B95"))
                            , 0, colorIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                })

    }
}