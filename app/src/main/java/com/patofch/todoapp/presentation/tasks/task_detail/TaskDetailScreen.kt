package com.patofch.todoapp.presentation.tasks.task_detail

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patofch.todoapp.domain.model.Task
import com.patofch.todoapp.domain.utils.toDateString
import com.patofch.todoapp.presentation.TaskViewModel
import com.patofch.todoapp.ui.theme.ToDoAppTheme

@Composable
fun TaskDetailScreen(
    viewModel: TaskViewModel,
    task: Task
) {
    Log.e("TaskDetailScreen", "TaskDetailScreen recompose")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TaskDetailContent(
            task = task
        )
    }
}

@Composable
fun TaskDetailContent(
    task: Task
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        TaskProperty("Id:", task.id.toString())
        TaskProperty("ParentId:", task.parentId.toString())
        TaskProperty("Name:", task.name)
        TaskProperty("Description:", task.description.toString())
        TaskProperty("Creation Date:", task.creationDate.toDateString())
        TaskProperty("Limit Date:", task.limitDate?.toDateString().orEmpty())
        TaskProperty("Status:", task.status.name)
        Spacer(Modifier.height(10.dp))
        Text(text = "Sub Tasks", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        Spacer(Modifier.height(10.dp))
        task.subTasks.forEachIndexed { index, task ->
            TaskProperty(name = "${index + 1} -", text = task.name, Modifier.padding(start = 15.dp).padding(10.dp))
        }
    }
}

@Composable
private fun TaskProperty(
    name: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "$name $text",
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp)
        )
        Divider()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ToDoAppTheme {
        TaskDetailContent(
            task = Task.TaskBuilder("Task1")
                .setDescription("esta es la descripción")
                .build().apply {
                    subTasks.addAll(
                        mutableListOf(
                            Task.TaskBuilder("SubTask 1 1")
                                .setParentId(1)
                                .build(),
                            Task.TaskBuilder("SubTask 1 2")
                                .setParentId(1)
                                .build(),
                            Task.TaskBuilder("SubTask 1 3")
                                .setParentId(1)
                                .build()
                        )
                    )
                }
        )
    }
}