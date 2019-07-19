package com.yhc.twtest.network

object ApiConstant {
    const val TW = "http://thoughtworks-ios.herokuapp.com/user/"

    @JvmStatic
    fun getHost(hostType: HostType): String {
        var url = TW

        when (hostType) {
            HostType.TW -> url = TW
            else -> url = ""
        }

        return url
    }
}