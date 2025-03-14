package io.jmtodoapp.project.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.jmtodoapp.todotask.db.TodoAppDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        val driver: SqlDriver = NativeSqliteDriver(
            TodoAppDatabase.Schema,
            "todo.db"
        )
        return driver
    }
}