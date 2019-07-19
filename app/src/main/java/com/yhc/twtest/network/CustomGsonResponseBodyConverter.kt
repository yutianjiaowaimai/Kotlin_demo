package com.yhc.twtest.network

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import org.json.JSONTokener
import retrofit2.Converter
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 * 参照GsonResponseBodyConverter，修改convert方法
 */
class CustomGsonResponseBodyConverter <T>(private val gson: Gson, private val adapter: TypeAdapter<T>) :
        Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        var response = value.string()

        //特殊字符处理
        if ("-" in response) {
            response = response.replace("-", "_")
        }

        //默认JSONObject
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream)
        val jsonReader = gson.newJsonReader(reader)
        try {
            return adapter.read(jsonReader)
        } finally {
            value.close()
        }

    }
}