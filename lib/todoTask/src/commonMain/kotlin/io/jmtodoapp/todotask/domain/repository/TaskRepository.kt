package io.jmtodoapp.todotask.domain.repository

import io.jmtodoapp.todotask.domain.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun getAllTasks(): Flow<List<TaskModel>>

    fun getTaskById(id: Long): Flow<TaskModel?>

    fun getTasksByCompleted(completed: Boolean): Flow<List<TaskModel>>

    fun getTasksByFavorite(favorite: Boolean): Flow<List<TaskModel>>

    suspend fun addTask(task: TaskModel): Result<Unit>

    suspend fun updateTask(task: TaskModel): Result<Unit>

    suspend fun deleteTask(id: Long): Result<Unit>

    suspend fun toggleFavorite(id: Long): Result<Unit?>

    suspend fun toggleCompleted(id: Long): Result<Unit>

    suspend fun deleteCompletedTasks(): Result<Unit>
}