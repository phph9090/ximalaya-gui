package me.phph.apps.ximalaya

import khttp.get
import khttp.responses.Response
import java.nio.charset.Charset

private val USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
private val HOME_URL_PREFIX = "https://www.ximalaya.com"
private val HEADERS = mapOf(
        "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
        "Accept-Encoding" to "gzip, deflate, br",
        "Accept-Language" to "zh-CN,zh;q=0.9,en;q=0.8,zh-TW;q=0.7,de;q=0.6",
        "Connection" to "keep-alive",
        "Host" to "www.ximalaya.com",
        "Sec-Fetch-Mode" to "navigate",
        "Sec-Fetch-Site" to "none",
        "Sec-Fetch-User" to "?1",
        "Upgrade-Insecure-Requests" to "1",
        "User-Agent" to USER_AGENT
)
private val DEFAULT_CHARSET = Charset.forName("utf-8")


private fun checkResponse(res: Response) {
    if (res.statusCode != 200) {
        throw ApiFailException("api failed")
    }
}

class DataApi {
    companion object {
        fun getAlbum(albumId: Int): String {
            val res = get("${HOME_URL_PREFIX}/revision/album", headers, mapOf("albumId" to albumId.toString()))
            checkResponse(res)
            return String(res.content, DEFAULT_CHARSET)
        }

        @JvmOverloads
        fun getAlbumTracks(albumId: Int, pageNum: Int = 1): String {
            val res = get("${HOME_URL_PREFIX}/revision/album/v1/getTracksList", headers, mapOf(
                    "albumId" to albumId.toString(),
                    "pageNum" to pageNum.toString()
            ))
            checkResponse(res)
            return String(res.content, DEFAULT_CHARSET)
        }

        @JvmOverloads
        fun getTrackDetail(id: Int, sort: Int = 0, size: Int = 1, ptype: Int = 1): String {
            val res = get("${HOME_URL_PREFIX}/revision/play/v1/show", headers, mapOf(
                    "id" to id.toString(),
                    "sort" to sort.toString(),
                    "size" to size.toString(),
                    "ptype" to ptype.toString()
            ))
            checkResponse(res)
            return String(res.content, DEFAULT_CHARSET)
        }

        @JvmOverloads
        fun getTrackUrl(id: Int, ptype: Int = 0): String {
            val res = get("${HOME_URL_PREFIX}/revision/play/v1/audio", headers, mapOf(
                    "id" to id.toString(),
                    "ptype" to ptype.toString()
            ))
            checkResponse(res)
            return String(res.content, DEFAULT_CHARSET)
        }
    }
}

fun main() {
    print(DataApi.getAlbum(9723091));
}

