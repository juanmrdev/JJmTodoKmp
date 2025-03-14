package io.jmtodoapp.todotask.presentation.detail

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jmtodoapp.todotask.domain.model.TaskModel
import io.jmtodoapp.todotask.domain.repository.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@Stable
data class TaskDetailState(
    val taskId: Long = -1,
    val task: TaskModel? = null,
    val title: String = "",
    val description: String = "",
    val errorMessage: String? = null
)

class TaskDetailViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskDetailState())
    val state: StateFlow<TaskDetailState> get() =  _state.asStateFlow()

    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            _state.update {
                it.copy(taskId = taskId)
            }

            repository.getTaskById(taskId).collect { task ->
                if (task != null) {
                    _state.update {
                        it.copy(
                            task = task,
                            title = task.title,
                            description = task.description
                        )
                    }
                } else {
                    _state.update {
                        it.copy(errorMessage = "Task not found")
                    }
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _state.value = _state.value.copy(title = newTitle)
    }

    fun onDescriptionChange(newDescription: String) {
        _state.value = _state.value.copy(description = newDescription)
    }

    fun onSave(onNavigateBack: () -> Unit) {
        viewModelScope.launch {
            val currentTask = _state.value.task ?: return@launch
            val updatedTask = currentTask.copy(
                title = _state.value.title,
                description = _state.value.description
            )
            val result = repository.updateTask(updatedTask)
            when {
                result.isSuccess -> onNavigateBack()
                result.isFailure -> _state.value =
                    _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun onDelete(onNavigateBack: () -> Unit) {
        viewModelScope.launch {
            val result = repository.deleteTask(state.value.taskId)
            when {
                result.isSuccess -> onNavigateBack()
                result.isFailure -> _state.value =
                    _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }
}