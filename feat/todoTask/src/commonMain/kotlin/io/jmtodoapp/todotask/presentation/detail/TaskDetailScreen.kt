package com.yourpackage.todotask.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.jmtodoapp.todotask.presentation.detail.TaskDetailState
import io.jmtodoapp.todotask.presentation.detail.TaskDetailViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TaskDetailScreen(
    modifier: Modifier = Modifier,
    taskId: Long,
    onNavigateBack: () -> Unit,
    viewModel: TaskDetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadTask(taskId)
    }

    TaskDetailScreenContent(
        modifier = modifier,
        state = state,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange,
        onSave = viewModel::onSave,
        onDelete = viewModel::onDelete,
        onClearError = viewModel::clearError,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun TaskDetailScreenContent(
    modifier: Modifier,
    state: TaskDetailState,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: (() -> Unit) -> Unit,
    onDelete: (() -> Unit) -> Unit,
    onClearError: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Task Details",
            style = MaterialTheme.typography.headlineMedium
        )

        state.errorMessage?.let {
            Text(
                text = "Task Details",
                color = Color.Red
            )
            return
        }

        OutlinedTextField(
            value = state.title,
            onValueChange = onTitleChange,
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.description,
            onValueChange = onDescriptionChange,
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { onSave(onNavigateBack) }
            ) {
                Text("Save")
            }

            Button(
                onClick = { onDelete(onNavigateBack) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Delete")
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