package io.jmtodoapp.todotask.presentation.list

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jmtodoapp.todotask.domain.model.TaskModel
import io.jmtodoapp.todotask.domain.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@Stable
data class TaskListState(
    val tasks: List<TaskModel> = emptyList(),
    val filterCompleted: Boolean? = null,
    val filterFavorite: Boolean? = null,
    val errorMessage: String? = null
)

class TaskListViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    
    private val _state = MutableStateFlow(TaskListState())
    val state: StateFlow<TaskListState> = _state.asStateFlow()

    init {
        collectTasks()
    }

    private fun collectTasks() {
        viewModelScope.launch {
            repository.getAllTasks()
                .combine(state) { tasks, currentState ->
                    val filteredTasks = tasks.filter { task ->
                        (currentState.filterCompleted?.let { task.completed == it } ?: true) &&
                                (currentState.filterFavorite?.let { task.favorite == it } ?: true)
                    }
                    currentState.copy(tasks = filteredTasks)
                }
                .collect { newState ->
                    _state.value = newState
                }
        }
    }

    fun onAddTask() {
        viewModelScope.launch {
            val result = repository.addTask(
                TaskModel(
                    title = "New Task",
                    description = "Description",
                    favorite = false,
                    completed = false
                )
            )
            if (result.isFailure) {
                _state.value = _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun onToggleCompleted(id: Long) {
        viewModelScope.launch {
            val result = repository.toggleCompleted(id)
            if (result.isFailure) {
                _state.value = _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun onToggleFavorite(id: Long) {
        viewModelScope.launch {
            val result = repository.toggleFavorite(id)
            if (result.isFailure) {
                _state.value = _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun onToggleFilterCompleted() {
        _state.value = _state.value.copy(
            filterCompleted = true.takeIf { _state.value.filterCompleted == null }
        )
    }

    fun onToggleFilterFavorite() {
        _state.value = _state.value.copy(
            filterFavorite = true.takeIf { _state.value.filterFavorite == null }
        )
    }

    fun onDeleteCompleted() {
        viewModelScope.launch {
            val result = repository.deleteCompletedTasks()
            if (result.isFailure) {
                _state.value = _state.value.copy(errorMessage = "Something was wrong")
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(errorMessage = null)
    }
}