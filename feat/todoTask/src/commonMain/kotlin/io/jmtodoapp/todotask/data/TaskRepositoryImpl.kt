package io.jmtodoapp.todotask.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import io.jmtodoapp.todotask.db.TaskTable
import io.jmtodoapp.todotask.db.TodoAppDatabase
import io.jmtodoapp.todotask.domain.model.TaskModel
import io.jmtodoapp.todotask.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    todoAppDatabase: TodoAppDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskRepository {
    private val queries = todoAppDatabase.taskDatabaseQueries

    override fun getAllTasks(): Flow<List<TaskModel>> {
        return queries.selectAll().asFlow()
            .mapToList(dispatcher)
            .map { table ->
                table.map(TaskTable::toTaskModel)
            }
    }

    override fun getTaskById(id: Long): Flow<TaskModel?> {
        return queries.selectById(id).asFlow()
            .mapToOne(dispatcher)
            .map(TaskTable::toTaskModel)
    }

    override fun getTasksByCompleted(completed: Boolean): Flow<List<TaskModel>> {
        return queries.selectByCompleted(completed.toInt()).asFlow()
            .mapToList(dispatcher)
            .map {
                it.map(TaskTable::toTaskModel)
            }
    }

    override fun getTasksByFavorite(favorite: Boolean): Flow<List<TaskModel>> {
        return queries.selectByCompleted(favorite.toInt()).asFlow()
            .mapToList(dispatcher)
            .map {
                it.map(TaskTable::toTaskModel)
            }

    }

    override suspend fun addTask(task: TaskModel): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.insert(
                    title = task.title,
                    description = task.description,
                    favorite = task.favorite.toInt(),
                    completed = task.completed.toInt()
                )
            }
        }
    }

    override suspend fun updateTask(task: TaskModel): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.update(
                    title = task.title,
                    description = task.description,
                    favorite = task.favorite.toInt(),
                    completed = task.completed.toInt(),
                    id = task.id
                )
            }
        }
    }

    override suspend fun deleteTask(id: Long): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.delete(
                    id = id
                )
            }
        }
    }

    override suspend fun toggleFavorite(id: Long): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.toggleFavorite(
                    id = id
                )
            }
        }
    }

    override suspend fun toggleCompleted(id: Long): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.toggleCompleted(
                    id = id
                )
            }
        }
    }

    override suspend fun deleteCompletedTasks(): Result<Unit> {
        return runCatching {
            queries.transaction {
                queries.deleteCompleted()
            }
        }
    }
}

private fun TaskTable.toTaskModel() = TaskModel(
    id = id,
    title = title,
    description = description,
    favorite = favorite != 0L,
    completed = completed != 0L
)

private fun Boolean.toInt() = if (this) 1L else 0L