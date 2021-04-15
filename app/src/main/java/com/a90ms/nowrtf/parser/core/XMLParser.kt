package com.a90ms.nowrtf.parser.core

import com.a90ms.nowrtf.parser.data.TrendsItem
import com.a90ms.nowrtf.parser.data.Channel
import com.a90ms.nowrtf.util.Constants
import com.a90ms.nowrtf.util.DateConverter
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import kotlin.jvm.Throws

object XMLParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXML(xml: String): Channel {

        val articleList = mutableListOf<TrendsItem>()
        var currentArticle = TrendsItem()

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()
        val reader: Reader = InputStreamReader(ByteArrayInputStream(xml.trim().toByteArray()))

        xmlPullParser.setInput(reader)

        var insideItem = false
        var insideNewItems = false

        var dateInit = true
        var preDate = ""
        var nowDate = ""
        var rank = 0
        var eventType = xmlPullParser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals(Constants.RSS_ITEM, ignoreCase = true)) {
                    insideItem = true
                } else if (xmlPullParser.name.equals(Constants.RSS_ITEM_NEWS, ignoreCase = true)) {
                    insideNewItems = true
                }

                if (insideItem) {
                    when (xmlPullParser.name) {
                        Constants.RSS_ITEM_TITLE -> currentArticle.title = xmlPullParser.nextText().trim()
                        Constants.RSS_ITEM_TRAFFIC -> currentArticle.traffic = xmlPullParser.nextText().trim()
                        Constants.RSS_ITEM_IMAGE -> currentArticle.mainImage = xmlPullParser.nextText().trim()
                        Constants.RSS_ITEM_IMAGE_SOURCE -> currentArticle.mainImageSource = xmlPullParser.nextText().trim()
                        Constants.RSS_ITEM_PUB_DATE -> {
                            val nextTokenType = xmlPullParser.next()
                            if (nextTokenType == XmlPullParser.TEXT) {
                                currentArticle.pubDate = xmlPullParser.text.trim()

                                if (preDate.isEmpty()) {
                                    preDate = DateConverter.changeDateFormat(xmlPullParser.text.trim())
                                }
                                nowDate = DateConverter.changeDateFormat(xmlPullParser.text.trim())

                                if (preDate != nowDate && !dateInit) {
                                    rank = 1
                                    dateInit = true
                                    preDate = DateConverter.changeDateFormat(xmlPullParser.text.trim())
                                    currentArticle.rank = rank
                                } else {
                                    rank += 1
                                    currentArticle.rank = rank
                                    dateInit = false
                                }
                            }
                        }
                        Constants.RSS_ITEM_NEWS -> {
                        }
                    }
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals(
                    Constants.RSS_ITEM,
                    ignoreCase = true
                )
            ) {
                insideItem = false
                articleList.add(currentArticle)
                currentArticle = TrendsItem()
            }
            eventType = xmlPullParser.next()
        }
        return Channel(
            trendsItems = articleList
        )
    }
}