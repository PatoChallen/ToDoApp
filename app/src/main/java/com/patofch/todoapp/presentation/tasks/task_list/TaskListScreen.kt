package com.patofch.todoapp.presentation.tasks.task_list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.patofch.todoapp.domain.model.task.Task
import com.patofch.todoapp.presentation.TaskViewModel

@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    Log.e("TaskListScreen", "TaskListScreen recompose")
    val status by viewModel.status
    val tasks by viewModel.tasks
    val categories by viewModel.categories
    var showNewTaskModal by remember {
        mutableStateOf(false)
    }
    var selectedTask: Task? by remember {
        mutableStateOf(null)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            is TaskViewModel.Status.Error -> {
                ErrorScreen(
                    errorMessage = (status as TaskViewModel.Status.Error).message!!,
                    onReload = {
                        viewModel.getTasks()
                    }
                )
            }

            TaskViewModel.Status.Loading -> {
                LoadingScreen()
            }

            is TaskViewModel.Status.Success -> {
                Column {
                    Text(text = "Clear", Modifier.fillMaxWidth().padding(10.dp).clickable {
                        viewModel.clearData()
                    })
                    LazyRow(Modifier.padding(10.dp)) {
                        items(categories) {
                            Box(
                                Modifier
                                    .padding(end = 10.dp)
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(it.color))
                            )
                        }
                    }
                    TaskScreenContent(
                        tasks = tasks,
                        selectedTask = selectedTask,
                        showNewTaskModal = showNewTaskModal,
                        onSelectTask = {
                            selectedTask = it
                            showNewTaskModal = true
                        },
                        onCreateTask = {
                            viewModel.insertTask(it)
                            showNewTaskModal = false
                        },
                        onDeleteTask = {
                            viewModel.deleteTask(it)
                            showNewTaskModal = false
                        },
                        onCancel = {
                            showNewTaskModal = false
                        },
                        onNewTask = {
                            showNewTaskModal = true
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun TaskScreenContent(
    tasks: List<Task>,
    selectedTask: Task?,
    showNewTaskModal: Boolean,
    onSelectTask: (Task) -> Unit,
    onCreateTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    onNewTask: () -> Unit,
    onCancel: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (showNewTaskModal.not()) {
                FloatingActionButton(onClick = onNewTask) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "add task")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            ToDoList(
                tasks = tasks,
                onSelectTask = onSelectTask,
                onDeleteTask = onDeleteTask
            )
            if (showNewTaskModal) {
                NewTask(
                    parentId = selectedTask?.id,
                    onCreateTask = onCreateTask,
                    onCancel = onCancel
                )
            }
        }
    )
}

@Composable
fun ToDoList(
    tasks: List<Task>,
    onSelectTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
) {
    Log.e("TaskListScreen", "TaskList recompose tasks")
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            items(tasks) { task ->
                TaskItem(onSelectTask, onDeleteTask, task)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun TaskItem(
    onSelectTask: (Task) -> Unit,
    onDeleteTask: (Task) -> Unit,
    task: Task
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Log.e("TaskListScreen", "TaskItem recompose")
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (task.subTasks.isEmpty()) {
                    onSelectTask(task)
                } else {
                    expanded = expanded.not()
                }
            }
            .padding(5.dp),
        backgroundColor = Color.White,
        elevation = 5.dp,
    ) {
        Column(
            Modifier.padding(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                )
                if (expanded) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "add sub task",
                        Modifier.clickable { onSelectTask(task) }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = "delete task",
                        Modifier.clickable { onDeleteTask(task) }
                    )
                }
            }
            AnimatedVisibility(expanded) {
                Column(Modifier.clickable(false) { }) {
                    task.subTasks.forEach {
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .fillMaxWidth()
                                .padding(5.dp),
                            backgroundColor = Color.White,
                            elevation = 5.dp,
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .weight(1f)
                                )
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "delete task",
                                    modifier = Modifier.clickable { onDeleteTask(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewTask(
    parentId: Int? = null,
    onCreateTask: (Task) -> Unit,
    onCancel: () -> Unit,
) {
    Log.e("TaskListScreen", "NewTask recompose parentId:$parentId")
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = .3f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(10.dp),
            backgroundColor = Color.White,
            elevation = 5.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                parentId?.let {
                    TextField(
                        value = it.toString(),
                        onValueChange = { },
                        enabled = false,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(5.dp))
                }
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(5.dp))
                TextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(onClick = onCancel) {
                        Text(text = "Cancel")
                    }
                    Button(
                        onClick = {
                            val newTask = Task.TaskBuilder(name)
                                .setDescription(description)
                                .setParentId(parentId)
                                .build()
                            onCreateTask(newTask)
                        },
                        enabled = name.isNotEmpty() && description.isNotEmpty()
                    ) {
                        Text(text = "Add Task")
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    CircularProgressIndicator()
}

@Composable
private fun ErrorScreen(
    errorMessage: String,
    onReload: () -> Unit
) {
    Column {
        Text(text = "ERROR\n$errorMessage", color = Color.Red)
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = onReload) {
            Text(text = "Reload")
        }
    }
}