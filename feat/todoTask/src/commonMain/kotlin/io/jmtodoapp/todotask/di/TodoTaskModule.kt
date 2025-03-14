package io.jmtodoapp.todotask.di

import io.jmtodoapp.todotask.data.TaskRepositoryImpl
import io.jmtodoapp.todotask.db.TodoAppDatabase
import io.jmtodoapp.todotask.domain.repository.TaskRepository
import io.jmtodoapp.todotask.presentation.detail.TaskDetailViewModel
import io.jmtodoapp.todotask.presentation.list.TaskListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun todoTaskModule(todoAppDatabase: TodoAppDatabase) = module {
    single<TaskRepository> { TaskRepositoryImpl(todoAppDatabase) }
    viewModelOf(::TaskDetailViewModel)
    viewModelOf(::TaskListViewModel)
}