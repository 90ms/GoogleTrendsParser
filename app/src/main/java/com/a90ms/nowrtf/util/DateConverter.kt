package com.a90ms.nowrtf.util

object DateConverter {

    fun getDayOfWeek(pubDate: String?): String {
        return when(pubDate?.substring(0,3)) {
            "Sun" -> "일요일"
            "Mon" -> "일요일"
            "Tue" -> "일요일"
            "Wed" -> "일요일"
            "Thu" -> "일요일"
            "Fri" -> "일요일"
            else -> "토요일"
        }
    }

    fun getDateTitle(pubDate: String?): String {
        return "${pubDate?.substring(12,16)}년" +
                " ${getMonth(pubDate)} " +
                "${pubDate?.substring(5,7)}일 " +
                "[${getDayOfWeek(pubDate)}]"
    }

    fun getDate(pubDate: String?): String {
        return "${pubDate?.substring(17,22)}"

    }

    fun getMonth(pubDate: String?): String {
        return when(pubDate?.substring(8,12)) {
            "Jan" -> "1월"
            "Feb" -> "2월"
            "Mar" -> "3월"
            "Apr" -> "4월"
            "May" -> "5월"
            "Jun" -> "6월"
            "Jul" -> "7월"
            "Aug" -> "8월"
            "Sep" -> "9월"
            "Oct" -> "10월"
            "Nov" -> "11월"
            else -> "12월"
        }
    }
}