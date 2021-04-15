package com.a90ms.nowrtf.util

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {

    fun changeDateFormat(pubDate: String?): String{
        val chageDate = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREAN)
        val date = Date(pubDate)

        return chageDate.format(date)
    }

    fun changeTimeFormat(pubDate: String?): String{
        val chageDate = SimpleDateFormat("HH:mm:ss", Locale.KOREAN)
        val date = Date(pubDate)

        return chageDate.format(date)
    }
}