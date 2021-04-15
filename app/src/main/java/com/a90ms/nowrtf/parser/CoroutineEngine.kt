package com.a90ms.nowrtf.parser

import com.a90ms.nowrtf.parser.core.XMLFetcher
import com.a90ms.nowrtf.parser.core.XMLParser
import com.a90ms.nowrtf.parser.data.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.nio.charset.Charset
import kotlin.jvm.Throws

object CoroutineEngine {
    @Throws(Exception::class)
    suspend fun fetchXML(url: String, okHttpClient: OkHttpClient?, charset: Charset): String =
        withContext(Dispatchers.IO) {
            return@withContext XMLFetcher.fetchXML(url, okHttpClient, charset)
        }

    @Throws(Exception::class)
    suspend fun parseXML(xml: String): Channel = withContext(Dispatchers.IO) {
        return@withContext XMLParser.parseXML(xml)
    }
}