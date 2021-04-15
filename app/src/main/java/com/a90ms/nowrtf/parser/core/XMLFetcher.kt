package com.a90ms.nowrtf.parser.core

import okhttp3.OkHttpClient
import okhttp3.Request
import java.nio.charset.Charset
import kotlin.jvm.Throws

object XMLFetcher {
    @Throws(Exception::class)
    fun fetchXML(url: String, okHttpClient: OkHttpClient? = null, charset: Charset): String {
        var client = okHttpClient
        if (client == null) {
            client = OkHttpClient()
        }
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        val byteStream = response.body()!!.byteStream()
        return byteStream.bufferedReader(charset).use { it.readText() }
    }
}