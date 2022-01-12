package com.patofch.todoapp.domain.use_case

import javax.inject.Inject

data class TasksUseCases @Inject constructor(
    val getAllTasks: GetAllTasks,
    val getAllSubTasks: GetAllSubTasks
)