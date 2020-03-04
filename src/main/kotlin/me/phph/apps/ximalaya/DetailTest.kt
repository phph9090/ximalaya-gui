package me.phph.apps.ximalaya

import khttp.get


fun main() {
//    val url = "https://www.ximalaya.com/revision/play/v1/audio?id=45982332&ptype=1"
    val url = "https://www.ximalaya.com/revision/play/v1/show?id=45982332&sort=0&size=30&ptype=1"
    val res = get(url, headers)
    val jsonStr = String(res.content, utf8)
    println(jsonStr)
}