package com.patofch.todoapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patofch.todoapp.presentation.TaskViewModel
import com.patofch.todoapp.presentation.tasks.task_list.TaskListScreen
import com.patofch.todoapp.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint

class TodoSplash2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.e("SplashActivity", "SplashActivity recompose")
            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                }
            }
        }
    }
}
