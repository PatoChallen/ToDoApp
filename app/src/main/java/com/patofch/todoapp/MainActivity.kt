package com.patofch.todoapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.presentation.ToDoViewModel
import com.patofch.todoapp.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<ToDoViewModel>()
            Log.e("MainActivity", "MainActivity recompose")
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ToDoScreen(viewModel)
                }
            }
        }
    }
}

@Composable
private fun ToDoScreen(viewModel: ToDoViewModel) {
    Log.e("MainActivity", "ToDoScreen recompose")
    viewModel.getTasks()
    val status by viewModel.status
    val tasks = viewModel.tasks
    var showNewTaskModal by remember {
        mutableStateOf(false)
    }
    var selectedTask: Task? by remember {
        mutableStateOf(null)
    }
    val context = LocalContext.current
//    Log.e("TAG", "ToDoScreen values $status - $tasks - ${tasks.value} - $showNewTaskModal - $selectedTask")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedTask = null
                showNewTaskModal = true
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add task")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                ToDoList(
                    tasks = tasks.value,
                    onSelectTask = {
                        selectedTask = it
                        showNewTaskModal = true
                    },
                    onDeleteTask = {
                        viewModel.deleteTask(it)
                        showNewTaskModal = false
                        selectedTask = null
                    }
                )
                if (showNewTaskModal) {
                    NewTask(
                        parentId = selectedTask?.id,
                        onCreateTask = {
                            viewModel.insertTask(it)
                            showNewTaskModal = false
                            selectedTask = null
                        },
                        onCancel = {
                            showNewTaskModal = false
                            selectedTask = null
                        }
                    )
                }
                when(status) {
                    is ToDoViewModel.Status.Error -> {
                        Column {
                            Text(text = "ERROR\n${(status as ToDoViewModel.Status.Error).message}", color = Color.Red)
                            Spacer(modifier = Modifier.height(15.dp))
                            Button(onClick = {
                                viewModel.getTasks()
                            }) {
                                Text(text = "Reload")
                            }
                        }
                    }
                    ToDoViewModel.Status.Loading -> CircularProgressIndicator()
                    is ToDoViewModel.Status.Success ->
                        context.showToast((status as ToDoViewModel.Status.Success).message.orEmpty())
                }
            }
        }
    )
}

@Composable
fun LayoutView() {
    Layout(
        content = {},
        modifier = Modifier.clickable {  },
        measurePolicy = MeasurePolicy { measurables, constraints ->
            layout(300, 300) {
                measurables.forEach {  }
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
    Log.e("MainActivity", "ToDoList recompose tasks")
    Box {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            val grouped = tasks.groupBy { it.name[0] }
            grouped
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
    Log.e("MainActivity", "TaskItem recompose")
    var expanded by remember {
        mutableStateOf(false)
    }
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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = task.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                )
                if (expanded) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = "add sub task", Modifier.clickable {
                        onSelectTask(task)
                    })
                } else {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete task", Modifier.clickable {
                        onDeleteTask(task)
                    })
                }
            }
            AnimatedVisibility(expanded) {
                Column(Modifier.clickable { }) {
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
                            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .weight(1f)
                                )
                                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete task", Modifier.clickable {
                                    onDeleteTask(it)
                                })
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
    Log.e("MainActivity", "NewTask recompose parentId:$parentId")
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
                   .padding(15.dp)) {
               parentId?.let {
                   TextField(value = it.toString(), onValueChange = { }, enabled = false, modifier = Modifier.fillMaxWidth())
                   Spacer(Modifier.height(5.dp))
               }
               TextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
               Spacer(Modifier.height(5.dp))
               TextField(value = description, onValueChange = { description = it }, modifier = Modifier.fillMaxWidth())
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
                           val newTask = Task(
                               parentId = parentId,
                               name = name,
                               description = description
                           )
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

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    TaskItem(onSelectTask = {}, onDeleteTask = {}, task = Task(name = "Task 2", description = "Description 2", subTasks = mutableListOf(
        Task(name = "Task 2.1", description = "Description 2.1"),
        Task(name = "Task 2.2", description = "Description 2.2")
    )))
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ToDoAppTheme {
////        NewTask(onTaskCreated = {}) {
////
////        }
//        ToDoList(listOf(
//            Task(name = "Task 1", description = "Description 1"),
//            Task(name = "Task 2", description = "Description 2", subTasks = mutableListOf(
//                Task(name = "Task 2.1", description = "Description 2.1"),
//                Task(name = "Task 2.2", description = "Description 2.2")
//                )),
//            Task(name = "Task 3", description = "Description 3"),
//            Task(name = "Task 4", description = "Description 4"),
//            Task(name = "Task 5", description = "Description 5")
//        )) {}
//    }
//}

fun Context.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}