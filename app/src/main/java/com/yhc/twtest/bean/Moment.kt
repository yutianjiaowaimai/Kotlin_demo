package com.yhc.twtest.bean

/**
 * 状态内容
 */
data class Moment(val content: String,
                  val images: List<Images>,
                  val sender: Sender,
                  val comments: List<Comments>,
                  val error: String)