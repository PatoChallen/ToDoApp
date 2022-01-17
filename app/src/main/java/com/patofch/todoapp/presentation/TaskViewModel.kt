package com.patofch.todoapp.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private val _tasks: MutableState<List<Task>> = mutableStateOf(listOf())
    val tasks: State<List<Task>> get() = _tasks

    private val _status: MutableState<Status> = mutableStateOf(Status.Loading)
    val status: MutableState<Status> get() = _status

    init {
        Log.e("TaskViewModel", "TaskViewModel init")
    }

    fun getTasks() {
        Log.e("TaskViewModel", "getTasks")
        _status.value = Status.Loading
        viewModelScope.launch {
            taskUseCases.getTasks()
                .catch {
                    _status.value = Status.Error("task loaded error: ${it.message}")
                }
                .collect {
                    _tasks.value = it
                    _status.value = Status.Success("task loaded success")
                }
        }
    }

    fun insertTask(task: Task) {
        Log.e("TaskViewModel", "insertTask")
        _status.value = Status.Loading
        viewModelScope.launch {
            taskUseCases.insertTask(task)
            _status.value = Status.Success("Insert Success")
        }
    }

    fun updateTask(task: Task) {
        Log.e("TaskViewModel", "updateTask")
        _status.value = Status.Loading
        viewModelScope.launch {
            taskUseCases.updateTask(task)
            _status.value = Status.Success("Update Success")
        }
    }

    fun deleteTask(task: Task) {
        Log.e("TaskViewModel", "deleteTask")
        _status.value = Status.Loading
        viewModelScope.launch {
            taskUseCases.deleteTask(task)
            _status.value = Status.Success("Delete Success")
        }
    }

    sealed class Status {
        object Loading : Status()
        class Success(val message: String? = null) : Status()
        class Error(val message: String?) : Status()
    }
}
