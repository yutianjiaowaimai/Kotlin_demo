package com.yhc.twtest.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yhc.twtest.R
import com.yhc.twtest.bean.Comments

class CommentAdapter(itemLayoutId: Int,data: List<Comments>): BaseQuickAdapter<Comments,BaseViewHolder>(itemLayoutId,data) {

    override fun convert(helper: BaseViewHolder, item: Comments) {
        helper.setText(R.id.tv_comment,item.content)
    }
}