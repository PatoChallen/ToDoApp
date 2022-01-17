package com.patofch.todoapp.data.data_source.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@Module
@DisableInstallInCheck
internal object SettingsModule {

    @Provides
    @Singleton
    internal fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("TODO_PREFERENCES", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun provideSettingsManager(preferences: SharedPreferences): SettingsManager {
        return SettingsManagerImpl(preferences)
    }
}