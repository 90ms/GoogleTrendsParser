package com.a90ms.nowrtf.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a90ms.nowrtf.parser.Parser
import com.a90ms.nowrtf.parser.data.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class MainViewModel : ViewModel() {
    private val url = "https://trends.google.com/trends/trendingsearches/daily/rss?geo=KR"
//    private val url = "https://www.androidauthority.com/feed"
    private lateinit var articleListLive: MutableLiveData<Channel>

    private val _rssChannel = MutableLiveData<Channel>()
    val rssChannel: LiveData<Channel> get() = _rssChannel

    private val okHttpClient by lazy {
        OkHttpClient()
    }

    fun fetchFeed(parser: Parser) {
        viewModelScope.launch {
            try {
                _rssChannel.postValue(parser.getChannel(url))
            } catch (e: Exception) {
                e.printStackTrace()
                _rssChannel.postValue(Channel( mutableListOf()))
            }
        }
    }

    fun fetchForUrlAndParseRawData(url: String) {
        val parser = Parser.Builder().build()

        viewModelScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            val result = okHttpClient.newCall(request).execute()
            val raw = runCatching { result.body()?.string() }.getOrNull()
            if (raw == null) {
//                _snackbar.postValue("Something went wrong!")
            } else {
                val channel = parser.parse(raw)
                _rssChannel.postValue(channel)
            }
        }
    }
}