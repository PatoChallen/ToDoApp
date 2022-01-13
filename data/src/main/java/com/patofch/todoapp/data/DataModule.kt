package com.patofch.todoapp.data

import com.patofch.todoapp.data.data_source.DatabaseModule
import com.patofch.todoapp.data.data_source.TaskDao
import com.patofch.todoapp.data.data_source.ToDoDatabase
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import com.patofch.todoapp.data.data_source.model.TaskDtoEntityMapperImpl
import com.patofch.todoapp.data.repository.ToDoRepositoryImpl
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
    internal fun provideTaskDtoEntityMapper(): TaskEntityMapper<TaskDtoEntity> {
        return TaskDtoEntityMapperImpl()
    }
//
//    @Provides
//    @Singleton
//    internal fun provideCharacterApiEntityMapper(): CharacterApiEntityMapper {
//        return CharacterApiEntityMapper()
//    }

    @Provides
    @Singleton
    internal fun provideToDoRepository(
        taskDao: TaskDao,
        taskDtoEntityMapper: TaskEntityMapper<TaskDtoEntity>
    ): ToDoRepository {
        return ToDoRepositoryImpl(
            taskDao = taskDao,
            mapper = taskDtoEntityMapper
        )
    }
}