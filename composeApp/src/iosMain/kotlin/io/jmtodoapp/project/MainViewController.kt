package io.jmtodoapp.project

import androidx.compose.ui.window.ComposeUIViewController
import io.jmtodoapp.project.database.DatabaseDriverFactory
import io.jmtodoapp.project.di.initKoin
import io.jmtodoapp.todotask.db.TodoAppDatabase
import io.jmtodoapp.todotask.di.todoTaskModule

@Suppress("UNUSED")
fun mainViewController() = ComposeUIViewController(
    configure = {
        initKoin {
            todoTaskModule(
                TodoAppDatabase(
                    driver = DatabaseDriverFactory().createDriver()
                )
            )
        }
    }
) { App() }