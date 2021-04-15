package com.a90ms.nowrtf.parser

import android.content.Context
import com.a90ms.nowrtf.parser.data.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import java.nio.charset.Charset
import kotlin.coroutines.CoroutineContext
import kotlin.jvm.Throws

class Parser private constructor(
    private var okHttpClient: OkHttpClient? = null,
    private val charset: Charset = Charsets.UTF_8,
) {
    private val parserJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parserJob + Dispatchers.Default

    @Deprecated("")
    constructor(okHttpClient: OkHttpClient? = null) : this(okHttpClient, Charsets.UTF_8)

    @Deprecated("")
    constructor() : this(null, Charsets.UTF_8)

    data class Builder(
        private var okHttpClient: OkHttpClient? = null,
        private var charset: Charset = Charsets.UTF_8,
        private var context: Context? = null,
        private var cacheExpirationMillis: Long? = null
    ) {
        fun context(context: Context) = apply { this.context = context }
        fun cacheExpirationMillis(cacheExpirationMillis: Long) = apply { this.cacheExpirationMillis = cacheExpirationMillis }

        fun build() = Parser(okHttpClient, charset)
    }

    @Throws(Exception::class)
    suspend fun getChannel(url: String): Channel? = withContext(coroutineContext) {
        val xml = CoroutineEngine.fetchXML(url, okHttpClient, charset)
        return@withContext CoroutineEngine.parseXML(xml)
    }
}