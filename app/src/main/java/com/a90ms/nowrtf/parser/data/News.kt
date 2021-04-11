package com.a90ms.nowrtf.parser.data

import java.io.Serializable

/**data class Channel(
val title: String? = null,
val link: String? = null,
val description: String? = null,
val image: Image? = null,
val lastBuildDate: String? = null,
val updatePeriod: String? = null,
val articles: MutableList<Article> = mutableListOf()
) : Serializable*/

data class News(
    val title: String? = null,
    val snippet: String? = null,
    val url: String? = null,
    val source: String? = null
) : Serializable