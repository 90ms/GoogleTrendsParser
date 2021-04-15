package com.a90ms.nowrtf.parser.data

import java.io.Serializable

data class Channel(
    val trendsItems: MutableList<TrendsItem> = mutableListOf()
) : Serializable