package io.jmtodoapp.project.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import io.jmtodoapp.todotask.db.TodoAppDatabase

fun createDriver(): SqlDriver {
    val driver: SqlDriver = NativeSqliteDriver(
        TodoAppDatabase.Schema,
        "TodoAppDatabase.db"
    )
    return driver
}