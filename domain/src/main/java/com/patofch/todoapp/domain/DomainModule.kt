package com.patofch.todoapp.domain

import com.patofch.todoapp.domain.repository.ToDoRepository
import com.patofch.todoapp.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton


@Module
@DisableInstallInCheck
object DomainModule {

    @Provides
    @Singleton
    fun provideTaskUseCases(
        toDoRepository: ToDoRepository
    ): TaskUseCases {
        return TaskUseCases(
            deleteTask = DeleteTask(toDoRepository),
            getTasks = GetTasks(toDoRepository),
            insertTask = InsertTask(toDoRepository),
            updateTask = UpdateTask(toDoRepository)
        )
    }
}