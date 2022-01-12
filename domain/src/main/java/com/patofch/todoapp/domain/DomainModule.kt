package com.patofch.todoapp.domain

import com.patofch.todoapp.domain.repository.ToDoRepository
import com.patofch.todoapp.domain.use_case.GetAllSubTasks
import com.patofch.todoapp.domain.use_case.GetAllTasks
import com.patofch.todoapp.domain.use_case.TasksUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton


@Module
@DisableInstallInCheck
object DomainModule {

    @Provides
    @Singleton
    fun provideCharacterUseCases(
        toDoRepository: ToDoRepository
    ): TasksUseCases {
        return TasksUseCases(
            getAllTasks = GetAllTasks(toDoRepository),
            getAllSubTasks = GetAllSubTasks(toDoRepository)
        )
    }
}