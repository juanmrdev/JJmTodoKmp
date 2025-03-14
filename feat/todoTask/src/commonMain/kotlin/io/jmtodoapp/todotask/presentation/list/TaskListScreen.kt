package io.jmtodoapp.todotask.presentation.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import io.jmtodoapp.todotask.presentation.component.TaskItem
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskListScreen(
    modifier: Modifier = Modifier,
    onNavigateToDetail: (Long) -> Unit,
    viewModel: TaskListViewModel = koinViewModel<TaskListViewModel>()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TaskListScreenContent(
        modifier = modifier,
        state = state,
        onAddTask = viewModel::onAddTask,
        onDeleteCompletedTask = viewModel::onDeleteCompleted,
        onNavigateToDetail = onNavigateToDetail,
        onToggleFavorite = viewModel::onToggleFavorite,
        onToggleCompleted = viewModel::onToggleCompleted,
        onToggleFilterFavorite = viewModel::onToggleFilterFavorite,
        onToggleFilterCompleted = viewModel::onToggleFilterCompleted,
        onClearError = viewModel::clearError
    )
}

@Preview
@Composable
private fun TaskListScreenContent(
    modifier: Modifier,
    state: TaskListState,
    onAddTask: () -> Unit,
    onDeleteCompletedTask: () -> Unit,
    onToggleFavorite: (Long) -> Unit,
    onToggleCompleted: (Long) -> Unit,
    onToggleFilterFavorite: () -> Unit,
    onToggleFilterCompleted: () -> Unit,
    onClearError: () -> Unit,
    onNavigateToDetail: (Long) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Todo App",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onAddTask,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add New Task")
        }

        Button(
            onClick = onDeleteCompletedTask,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Delete Completed")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = state.filterCompleted != null,
                onClick = onToggleFilterCompleted,
                label = { Text("Completed") }
            )
            FilterChip(
                selected = state.filterFavorite != null,
                onClick = onToggleFilterFavorite,
                label = { Text("Favorite") }
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items = state.tasks, key = { it.id }) { task ->
                TaskItem(
                    task = task,
                    onClick = { onNavigateToDetail(task.id) },
                    onToggleCompleted = { onToggleCompleted(task.id) },
                    onToggleFavorite = { onToggleFavorite(task.id) }
                )
            }
        }

        state.errorMessage?.let { error ->
            Text(text = error, color = Color.Red)
            Button(onClick = onClearError) {
                Text("Clear Error")
            }
        }
    }
}