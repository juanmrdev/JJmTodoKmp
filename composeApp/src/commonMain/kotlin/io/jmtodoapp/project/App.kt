package io.jmtodoapp.project

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.jmtodoapp.project.designSystem.TodoAppTheme
import io.jmtodoapp.project.navigation.Graph
import io.jmtodoapp.project.navigation.navHost
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    TodoAppTheme {
        Scaffold {
            val navController = rememberNavController()
            navHost(
                navController = navController,
                startDestination = Graph.Task
            )
        }
    }
}