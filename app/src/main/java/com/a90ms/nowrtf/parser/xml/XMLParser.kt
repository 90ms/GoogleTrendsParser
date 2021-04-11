package com.a90ms.nowrtf.parser.xml

import com.a90ms.nowrtf.parser.core.CoreXMLParser
import com.a90ms.nowrtf.parser.data.Channel
import java.util.concurrent.Callable
import kotlin.jvm.Throws

class XMLParser(val xml: String) : Callable<Channel> {
    @Throws(Exception::class)
    override fun call(): Channel {
        return CoreXMLParser.parseXML(xml)
    }
}