package com.patofch.todoapp.data.data_source

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideTodoDatabase(app: Application): ToDoDatabase {
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            ToDoDatabase.DATABASE_NAME
        ).build()
    }
}