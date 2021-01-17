package ru.akirakozov.sd.refactoring.db

import java.sql.DriverManager
import java.sql.Statement

open class DBProvider(private val url: String) {
    fun <T> withStatement(action: (Statement) -> T): T {
        DriverManager.getConnection(url).use { c ->
            c.createStatement().use { s ->
                return action(s)
            }
        }
    }
}