package com.a90ms.nowrtf.parser.data

import java.io.Serializable

data class Channel(
    val articles: MutableList<Article> = mutableListOf()
) : Serializable