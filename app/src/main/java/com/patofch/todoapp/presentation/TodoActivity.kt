package com.patofch.todoapp.presentation

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patofch.todoapp.data.data_source.settings.SettingsManager
import com.patofch.todoapp.presentation.tasks.task_list.TaskListScreen
import com.patofch.todoapp.ui.theme.ToDoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TodoActivity : ComponentActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TaskViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("TodoActivity", "TodoActivity onCreate")
        val splashScreen = installSplashScreen()
        viewModel.apply {
            getTasks()
            getCategories()
        }
        val content: View = ComposeView(this).apply {
            setContent {
                Log.e("TodoActivity", "TodoActivity recompose")
                ToDoAppTheme {
                    // A surface container using the 'background' color from the theme
                    Surface(color = MaterialTheme.colors.background) {
                        TaskListScreen(viewModel)
                    }
                }
            }
        }
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    Log.e("TodoActivity", "onPreDraw")
                    return if (viewModel.isReady()) {
                        Log.e("TodoActivity", "onPreDraw true")
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        Log.e("TodoActivity", "onPreDraw false")
                        false
                    }
                }
            }
        )
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            ObjectAnimator.ofFloat(
                splashScreenView.view,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.view.height.toFloat()
            ).apply {
                interpolator = AnticipateInterpolator()
                duration = 200L
                doOnEnd { splashScreenView.remove() }
                start()
            }
        }
        setContentView(content)
//        setContent {
//            Log.e("TodoActivity", "TodoActivity recompose")
//            ToDoAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    TaskListScreen(viewModel)
//                }
//            }
//        }
    }
}