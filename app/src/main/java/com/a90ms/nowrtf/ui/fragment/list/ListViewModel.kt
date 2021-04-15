package com.a90ms.nowrtf.ui.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.a90ms.nowrtf.parser.Parser
import com.a90ms.nowrtf.parser.data.Channel
import kotlinx.coroutines.launch

class ListViewModel : ViewModel() {
    private val url = "https://trends.google.com/trends/trendingsearches/daily/rss?geo=KR"

    private val _rssChannel = MutableLiveData<Channel>()
    val rssChannel: LiveData<Channel> get() = _rssChannel

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
}