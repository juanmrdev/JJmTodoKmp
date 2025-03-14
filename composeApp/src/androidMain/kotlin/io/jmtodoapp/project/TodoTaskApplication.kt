package io.jmtodoapp.project

import android.app.Application
import io.jmtodoapp.project.database.DatabaseDriverFactory
import io.jmtodoapp.project.di.initKoin
import io.jmtodoapp.todotask.db.TodoAppDatabase
import io.jmtodoapp.todotask.di.todoTaskModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class TodoTaskApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TodoTaskApplication)
            androidLogger()
            modules(
                todoTaskModule(
                    TodoAppDatabase(
                        driver = DatabaseDriverFactory(this@TodoTaskApplication).createDriver()
                    )
                )
            )
        }
    }
}