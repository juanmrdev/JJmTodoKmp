package io.jmtodoapp.todotask.domain.model

data class TaskModel(
    val id: Long = 0L,
    val title: String,
    val description: String,
    val favorite: Boolean,
    val completed: Boolean,
)