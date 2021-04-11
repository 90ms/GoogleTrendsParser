package com.a90ms.nowrtf.parser.core

import android.util.Log
import com.a90ms.nowrtf.parser.data.Article
import com.a90ms.nowrtf.parser.data.Channel
import com.a90ms.nowrtf.util.DateConverter
import com.a90ms.nowrtf.util.RSSKeywords
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import kotlin.jvm.Throws

object CoreXMLParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parseXML(xml: String): Channel {

        val articleList = mutableListOf<Article>()
        var currentArticle = Article()

        val factory = XmlPullParserFactory.newInstance()
        factory.isNamespaceAware = false

        val xmlPullParser = factory.newPullParser()
        val reader: Reader = InputStreamReader(ByteArrayInputStream(xml.trim().toByteArray()))

        xmlPullParser.setInput(reader)

        // A flag just to be sure of the correct parsing
        var insideItem = false
        var insideNewItems = false

        var eventType = xmlPullParser.eventType

        // Start parsing the xml
        while (eventType != XmlPullParser.END_DOCUMENT) {

            // Start parsing the item
            if (eventType == XmlPullParser.START_TAG) {
                if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM, ignoreCase = true)) {
                    insideItem = true
                } else if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_NEWS, ignoreCase = true)) {
                    insideNewItems = true
                }

                if (insideItem) {

                    when(xmlPullParser.name) {
                        RSSKeywords.RSS_ITEM_TITLE -> currentArticle.title = xmlPullParser.nextText().trim()
                        RSSKeywords.RSS_ITEM_TRAFFIC -> currentArticle.traffic = xmlPullParser.nextText().trim()
                        RSSKeywords.RSS_ITEM_IMAGE -> currentArticle.mainImage = xmlPullParser.nextText().trim()
                        RSSKeywords.RSS_ITEM_IMAGE_SOURCE -> currentArticle.mainImageSource = xmlPullParser.nextText().trim()
                        RSSKeywords.RSS_ITEM_PUB_DATE -> {

                            val nextTokenType = xmlPullParser.next()
                            if (nextTokenType == XmlPullParser.TEXT) {
                                currentArticle.pubDate = xmlPullParser.text.trim()
                            }

//                            currentArticle.pubDate = xmlPullParser.text.trim()

                        }
                        RSSKeywords.RSS_ITEM_NEWS -> {

                        }
                    }

//                    if (xmlPullParser.name.equals(RSSKeywords.RSS_ITEM_TITLE, ignoreCase = true)
//                    ) {
//                        currentArticle.title = xmlPullParser.nextText().trim()
//                    } else if (xmlPullParser.name.equals(
//                            RSSKeywords.RSS_ITEM_IMAGE,
//                            ignoreCase = true
//                        )
//                    ) {
//                        currentArticle.mainImage = xmlPullParser.nextText().trim()
//                    } else if (xmlPullParser.name.equals(
//                            RSSKeywords.RSS_ITEM_IMAGE_SOURCE,
//                            ignoreCase = true
//                        )
//                    ) {
//                        currentArticle.mainImageSource = xmlPullParser.nextText().trim()
//                    } else if (xmlPullParser.name.equals(
//                            RSSKeywords.RSS_ITEM_PUB_DATE,
//                            ignoreCase = true
//                        )
//                    ) {
//                        val nextTokenType = xmlPullParser.next()
//                        if (nextTokenType == XmlPullParser.TEXT) {
//                            currentArticle.pubDate = xmlPullParser.text.trim()
//                        }
//                        continue
//                    } else if (xmlPullParser.name.equals(
//                            RSSKeywords.RSS_ITEM_NEWS,
//                            ignoreCase = true
//                        )
//                    ) {
//                    }
                } else {
                    Log.w("aa", "aaa")
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.name.equals(
                    RSSKeywords.RSS_ITEM,
                    ignoreCase = true
                )
            ) {
                // The item is correctly parsed
                insideItem = false
                articleList.add(currentArticle)
                currentArticle = Article()
            }
            eventType = xmlPullParser.next()
        }
        return Channel(
            articles = articleList
        )
    }
}