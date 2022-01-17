package com.patofch.todoapp.domain

import androidx.room.RoomDatabase
import com.patofch.todoapp.domain.repository.CategoryRepository
import com.patofch.todoapp.domain.repository.TaskRepository
import com.patofch.todoapp.domain.use_case.ClearDatabase
import com.patofch.todoapp.domain.use_case.category.CategoryUseCases
import com.patofch.todoapp.domain.use_case.category.DeleteCategory
import com.patofch.todoapp.domain.use_case.category.GetCategories
import com.patofch.todoapp.domain.use_case.category.InsertCategory
import com.patofch.todoapp.domain.use_case.task.DeleteTask
import com.patofch.todoapp.domain.use_case.task.GetTasks
import com.patofch.todoapp.domain.use_case.task.InsertTask
import com.patofch.todoapp.domain.use_case.task.TaskUseCases
import com.patofch.todoapp.domain.use_case.task.UpdateTask
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
object DomainModule {

    @Provides
    @Singleton
    fun provideClearDatabaseUseCase(database: RoomDatabase): ClearDatabase {
        return ClearDatabase(database)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(
        taskRepository: TaskRepository
    ): TaskUseCases {
        return TaskUseCases(
            deleteTask = DeleteTask(taskRepository),
            getTasks = GetTasks(taskRepository),
            insertTask = InsertTask(taskRepository),
            updateTask = UpdateTask(taskRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCategoryUseCases(
        categoryRepository: CategoryRepository
    ): CategoryUseCases {
        return CategoryUseCases(
            deleteCategory = DeleteCategory(categoryRepository),
            getCategories = GetCategories(categoryRepository),
            insertCategory = InsertCategory(categoryRepository)
        )
    }
}