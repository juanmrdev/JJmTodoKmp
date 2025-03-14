package io.jmtodoapp.project.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object TaskList : Route
    @Serializable
    data class TaskDetail(val taskId: Long) : Route
}

sealed interface Graph {
    @Serializable
    data object Task: Graph
}