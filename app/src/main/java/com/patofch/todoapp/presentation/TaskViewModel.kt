package com.patofch.todoapp.presentation

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patofch.todoapp.data.data_source.settings.SettingsManager
import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.domain.use_case.ClearDatabase
import com.patofch.todoapp.domain.use_case.category.CategoryUseCases
import com.patofch.todoapp.domain.use_case.task.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    private val categoryUseCases: CategoryUseCases,
    private val settings: SettingsManager,
    private val clearDatabase: ClearDatabase
) : ViewModel() {

    private val _tasks: MutableState<List<Task>> = mutableStateOf(listOf())
    val tasks: State<List<Task>> get() = _tasks

    private val _categories: MutableState<List<Category>> = mutableStateOf(listOf())
    val categories: State<List<Category>> get() = _categories

    private val _status: MutableState<Status> = mutableStateOf(Status.Loading)
    val status: MutableState<Status> get() = _status

    private var isReady = true

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
                    Log.e("TaskViewModel", "getTasks collect ${it.size}")
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

    fun getCategories() {
        Log.e("TaskViewModel", "getCategories")
        viewModelScope.launch {
            categoryUseCases.getCategories()
                .collect {
                    Log.e("TaskViewModel", "getCategories collect ${it.size}")
//                    if (it.isNotEmpty()) {
//                        isReady = true
//                    }
                    _categories.value = it
                }
        }
        if (settings.isFirstRun()) {
            settings.registerFirstRun()
            populateCategories()
        }
    }

    fun insertCategory(category: Category) {
        Log.e("TaskViewModel", "insertCategory")
        viewModelScope.launch {
            categoryUseCases.insertCategory(category)
        }
    }

    fun deleteCategory(category: Category) {
        Log.e("TaskViewModel", "deleteCategory")
        viewModelScope.launch {
            categoryUseCases.deleteCategory(category)
        }
    }

    private fun populateCategories() {
        Log.e("TaskViewModel", "populateCategories")
        isReady = false
        val initialCategories = listOf(
            Category(name = "Compras", color = Color.Cyan.value),
            Category(name = "Tareas", color = Color.Green.value)
        )
        viewModelScope.launch {
            initialCategories.forEach {
                categoryUseCases.insertCategory(it)
            }
            isReady = true
        }
    }

    fun clearData() {
        settings.clearData()
        clearDatabase()
    }

    fun isReady(): Boolean = isReady

    sealed class Status {
        object Loading : Status()
        class Success(val message: String? = null) : Status()
        class Error(val message: String?) : Status()
    }
}
