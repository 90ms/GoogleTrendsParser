package com.a90ms.nowrtf.parser.data

import java.io.Serializable

data class Article(

    var title: String? = null,
    var link: String? = null,
    var pubDate: String? = null,
    var description: String? = null,
    var traffic: String? = null,
    var mainImage: String? = null,
    var mainImageSource: String? = null,
    var sourceName: String? = null,
    var sourceUrl: String? = null,
    private var _news: MutableList<News> = mutableListOf(),

): Serializable {

    val news: MutableList<News>
        get() = _news

    fun addNews(news: News) {
        _news.add(news)
    }


}