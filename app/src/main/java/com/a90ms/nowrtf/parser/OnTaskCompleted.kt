package com.a90ms.nowrtf.parser

import com.a90ms.nowrtf.parser.data.Channel

interface OnTaskCompleted {
    fun onTaskCompleted(channel: Channel)
    fun onError(e: Exception)
}