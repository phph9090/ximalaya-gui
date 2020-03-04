package me.phph.apps.ximalaya

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import java.io.File

val DEFAULT_DATA_DIRECTORY = ".ximalaya_data/"

fun getDataDirectoryPath(): String {
    val homeDirectory = System.getProperty("user.home")
    return "$DEFAULT_DATA_DIRECTORY$homeDirectory/"
}

fun getDataDirectoryPath(albumId: String): String {
    val p = getDataDirectoryPath() + "/" + albumId + "/"
    val f = File(p)
    if (!f.exists()) {
        if (!f.mkdirs()) {
            throw RuntimeException("failed to create album directory")
        }
    }
    return p
}

fun initData() {
    val f = File(getDataDirectoryPath())
    if (!f.exists()) {
        if (f.mkdirs()) {
            throw RuntimeException("failed to create data directory")
        }
    }
}

fun readAlbums(): List<String> {
    val f = File(getDataDirectoryPath() + "albums")
    return if (f.exists()) {
        f.readLines()
    } else {
        listOf()
    }
}

fun saveAlbums(albums: List<String>) {
    val f = File(getDataDirectoryPath() + "albums")
    f.bufferedWriter().use { pr -> albums.forEach { pr.write(it + '\n') } }
}

fun readAlbumTracks(albumId: Int): List<String> {
    val f = File(getDataDirectoryPath() + albumId + "/info")
    return if (f.exists()) {
        f.readLines()
    } else {
        listOf()
    }
}

fun saveAlbumTracks(albumId: Int, tracks: List<String>) {
    val p = getDataDirectoryPath() + albumId + "/"
    val f = File(p)
    if (!f.exists()) {
        if (!f.mkdirs()) {
            throw RuntimeException("failed to create album directory")
        }
    }
    File("$p/$albumId/info").writer().use { pr -> tracks.forEach { pr.write(it + '\n') } }
}

/**
 * 1. call api to get tracks
 * 2. save album and tracks into db
 */
fun setupAlbum(id: Int) {
    var pageNum = 1
    var d = DataApi.getAlbumTracks(id, pageNum)
    var jo = JSONObject.parseObject(d)
    var ret = jo.getInteger("ret")
    if (ret != 200) {
        throw RuntimeException("api return none 200 code $ret")
    }
    val totalCount = jo.getInteger("trackTotalCount")
    val data = JSONArray()
    for (o in jo.getJSONObject("data").getJSONArray("tracks")) {
        data.add(o)
    }
    while (data.size != totalCount) {
        pageNum++
        d = DataApi.getAlbumTracks(id, pageNum)
        jo = JSONObject.parseObject(d)
        ret = jo.getInteger("ret")
        if (ret != 200) {
            throw RuntimeException("api return none 200 code $ret")
        }
        for (o in jo.getJSONObject("data").getJSONArray("tracks")) {
            data.add(o)
        }
    }
}


fun downloadPicture() {

}

fun downloadAudio() {

}