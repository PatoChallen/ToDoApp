package com.patofch.todoapp.data.data_source.settings

interface SettingsManager {

    fun isFirstRun(): Boolean

    fun registerFirstRun()

    fun clearData()
}