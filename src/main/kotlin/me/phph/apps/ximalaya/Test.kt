package me.phph.apps.ximalaya

import khttp.get
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.nio.charset.Charset

val HOME_URL = "https://www.ximalaya.com"



val utf8 = Charset.forName("utf-8")

val headers = mapOf(
        Pair("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Pair("Accept-Encoding", "gzip, deflate, br"),
        Pair("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,de;q=0.6"),
        Pair("Connection", "keep-alive"),
        Pair("Host", "www.ximalaya.com"),
        Pair("Sec-Fetch-Mode", "navigate"),
        Pair("Sec-Fetch-Site", "none"),
        Pair("Sec-Fetch-User", "?1"),
        Pair("Upgrade-Insecure-Requests", "1")
//        Pair("User-Agent", USER_AGENT)
)

fun extractAllTrackList(url: String): List<String> {
    var res = get(url, headers)
    var html = String(res.content, utf8)
    var doc = Jsoup.parse(html)

    val trackUrlList = mutableListOf<String>()

    val ele = doc.getElementsByClass("sound-list").first()
    trackUrlList.addAll(extractTrackList(ele))
    val pageUrlList = mutableListOf<String>()

    for (li in ele.getElementsByClass("pagination").first().getElementsByClass("page-item")) {
        if (!li.hasClass("active")) {
            pageUrlList.add(li.getElementsByTag("a").first().attr("href"))
        }
    }

    for (pageUrl in pageUrlList) {
        res = get(HOME_URL + pageUrl, headers)
        html = String(res.content, utf8)
        doc = Jsoup.parse(html)
        val targetEle = doc.getElementsByClass("sound-list").first()
        trackUrlList.addAll(extractTrackList(targetEle))
    }

    return trackUrlList
}

fun extractTrackList(ele: Element): List<String> {
    // element is ul
    val ret = mutableListOf<String>()
    for (li in ele.child(0).getElementsByTag("li")) {
        ret.add(li.getElementsByClass("text").first().getElementsByTag("a").first().attr("href"))
    }
    return ret
}


fun main() {
    val url = "https://www.ximalaya.com/xiangsheng/9723091/"

    val trackUrlList = extractAllTrackList(url)
    trackUrlList.forEach(::println)
}

