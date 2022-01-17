package com.patofch.todoapp.data

import com.patofch.todoapp.data.data_source.CategoryDao
import com.patofch.todoapp.data.data_source.DatabaseModule
import com.patofch.todoapp.data.data_source.TaskDao
import com.patofch.todoapp.data.data_source.ToDoDatabase
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntityMapperImpl
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import com.patofch.todoapp.data.data_source.model.TaskDtoEntityMapperImpl
import com.patofch.todoapp.data.repository.ToDoRepositoryImpl
import com.patofch.todoapp.domain.model.CategoryEntityMapper
import com.patofch.todoapp.domain.model.TaskEntityMapper
import com.patofch.todoapp.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module(
    includes = [
        DatabaseModule::class
    ]
)
@DisableInstallInCheck
object DataModule {

    @Provides
    @Singleton
    internal fun provideTaskDao(database: ToDoDatabase): TaskDao {
        return database.taskDao
    }

    @Provides
    @Singleton
    internal fun provideCategoryDao(database: ToDoDatabase): CategoryDao {
        return database.categoryDao
    }

    @Provides
    @Singleton
    internal fun provideTaskDtoEntityMapper(): TaskEntityMapper<TaskDtoEntity> {
        return TaskDtoEntityMapperImpl()
    }

    @Provides
    @Singleton
    internal fun provideCategoryDtoEntityMapper(): CategoryEntityMapper<CategoryDtoEntity> {
        return CategoryDtoEntityMapperImpl()
    }

    @Provides
    @Singleton
    internal fun provideToDoRepository(
        taskDao: TaskDao,
        taskDtoEntityMapper: TaskEntityMapper<TaskDtoEntity>,
        categoryDao: CategoryDao,
        categoryEntityMapper: CategoryEntityMapper<CategoryDtoEntity>
    ): ToDoRepository {
        return ToDoRepositoryImpl(
            taskDao = taskDao,
            categoryDao = categoryDao,
            taskEntityMapper = taskDtoEntityMapper,
            categoryEntityMapper = categoryEntityMapper
        )
    }
}