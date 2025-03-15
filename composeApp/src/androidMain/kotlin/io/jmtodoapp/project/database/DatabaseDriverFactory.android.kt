package io.jmtodoapp.project.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import io.jmtodoapp.todotask.db.TodoAppDatabase

fun createDriver(context: Context): SqlDriver {
    val driver = AndroidSqliteDriver(
        TodoAppDatabase.Schema,
        context,
        "TodoAppDatabase.db"
    )
    return driver
}