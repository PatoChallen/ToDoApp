package com.patofch.todoapp.data.data_source.settings

import android.content.SharedPreferences

class SettingsManagerImpl(
    private val preferences: SharedPreferences
) : SettingsManager {

    override fun isFirstRun(): Boolean = preferences.getBoolean(IS_FIRST_RUN, true)

    override fun registerFirstRun() = preferences.edit().putBoolean(IS_FIRST_RUN, false).apply()

    override fun clearData() = preferences.edit().clear().apply()
}