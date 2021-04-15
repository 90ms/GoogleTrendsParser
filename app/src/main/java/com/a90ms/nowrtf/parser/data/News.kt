package com.a90ms.nowrtf.parser.data

import java.io.Serializable

data class News(
    val title: String? = null,
    val snippet: String? = null,
    val url: String? = null,
    val source: String? = null
) : Serializable