package io.jmtodoapp.project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.yourpackage.todotask.ui.TaskDetailScreen
import io.jmtodoapp.todotask.presentation.list.TaskListScreen

@Composable
fun navHost(
    navController: NavHostController,
    startDestination: Any = Graph.Task
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Graph.Task>(startDestination = Route.TaskList) {
            composable<Route.TaskList> {
                TaskListScreen(
                    onNavigateToDetail = { taskId ->
                        navController.navigate(Route.TaskDetail(taskId))
                    }
                )
            }

            composable<Route.TaskDetail> {
                val argument = it.toRoute<Route.TaskDetail>().taskId

                TaskDetailScreen(
                    taskId = argument,
                    onNavigateBack = navController::navigateUp
                )
            }
        }
    }
}