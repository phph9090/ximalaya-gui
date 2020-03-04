package me.phph.apps.ximalaya

import java.sql.Connection
import java.sql.DriverManager

var conn: Connection? = null

fun initDb() {
    if (conn == null) {
        conn = DriverManager.getConnection("jdbc:sqlite:ximalaya.db")
    }
}

