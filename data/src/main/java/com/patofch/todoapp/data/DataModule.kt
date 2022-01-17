package com.patofch.todoapp.data

import com.patofch.todoapp.data.data_source.database.DatabaseModule
import com.patofch.todoapp.data.data_source.settings.SettingsModule
import com.patofch.todoapp.data.repository.CategoryRepositoryImpl
import com.patofch.todoapp.data.repository.TaskRepositoryImpl
import com.patofch.todoapp.domain.repository.CategoryRepository
import com.patofch.todoapp.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module(
    includes = [
        DatabaseModule::class,
        SettingsModule::class
    ]
)
@DisableInstallInCheck
abstract class DataModule {

    @Binds
    @Singleton
    internal abstract fun provideCategoryRepository(repository: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    internal abstract fun provideTaskRepository(repository: TaskRepositoryImpl): TaskRepository
}