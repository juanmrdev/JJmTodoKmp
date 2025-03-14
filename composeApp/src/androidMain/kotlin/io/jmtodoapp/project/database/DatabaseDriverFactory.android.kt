package io.jmtodoapp.project.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.jmtodoapp.todotask.db.TodoAppDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        val driver = AndroidSqliteDriver(
            TodoAppDatabase.Schema,
            context,
            "todo.db"
        )
        return driver
    }
}