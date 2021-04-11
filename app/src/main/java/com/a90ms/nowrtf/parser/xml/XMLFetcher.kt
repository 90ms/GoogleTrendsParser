package com.a90ms.nowrtf.parser.xml

import com.a90ms.nowrtf.parser.core.CoreXMLFetcher
import okhttp3.OkHttpClient
import java.nio.charset.Charset
import java.util.concurrent.Callable
import kotlin.jvm.Throws

class XMLFetcher(
    private val url: String,
    private val okHttpClient: OkHttpClient?,
    private val charset: Charset
) :
    Callable<String> {

    @Throws(Exception::class)
    override fun call(): String {
        return CoreXMLFetcher.fetchXML(url = url, okHttpClient = okHttpClient, charset = charset)
    }
}