package io.jmtodoapp.project

import androidx.compose.ui.window.ComposeUIViewController
import io.jmtodoapp.project.database.createDriver
import io.jmtodoapp.project.di.initKoin
import io.jmtodoapp.todotask.db.TodoAppDatabase
import io.jmtodoapp.todotask.di.todoTaskModule

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin {
            todoTaskModule(
                TodoAppDatabase(
                    driver = createDriver()
                )
            )
        }
    },
    content = { App() }
)