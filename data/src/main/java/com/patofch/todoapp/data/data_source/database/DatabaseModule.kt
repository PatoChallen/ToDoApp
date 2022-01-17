package com.patofch.todoapp.data.data_source.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntity
import com.patofch.todoapp.data.data_source.model.CategoryDtoEntityMapperImpl
import com.patofch.todoapp.data.data_source.model.TaskDtoEntity
import com.patofch.todoapp.data.data_source.model.TaskDtoEntityMapperImpl
import com.patofch.todoapp.domain.model.category.CategoryEntityMapper
import com.patofch.todoapp.domain.model.task.TaskEntityMapper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal abstract class DatabaseModule {

    @Binds
    @Singleton
    internal abstract fun provideTaskDtoEntityMapper(mapper: TaskDtoEntityMapperImpl): TaskEntityMapper<TaskDtoEntity>

    @Binds
    @Singleton
    internal abstract fun provideCategoryDtoEntityMapper(mapper: CategoryDtoEntityMapperImpl): CategoryEntityMapper<CategoryDtoEntity>

    @Binds
    @Singleton
    internal abstract fun provideRoomDataBase(database: ToDoDatabase): RoomDatabase

    companion object {

        @Provides
        @Singleton
        internal fun provideTodoDatabase(app: Application): ToDoDatabase {
            return Room.databaseBuilder(
                app,
                ToDoDatabase::class.java,
                ToDoDatabase.DATABASE_NAME
            ).build()
        }

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
    }
}