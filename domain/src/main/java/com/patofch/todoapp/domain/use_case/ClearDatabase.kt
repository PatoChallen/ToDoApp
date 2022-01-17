package com.patofch.todoapp.domain.use_case

import androidx.room.RoomDatabase
import com.patofch.todoapp.domain.model.category.Category
import com.patofch.todoapp.domain.repository.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ClearDatabase @Inject constructor(
    private val database: RoomDatabase
) {

    operator fun invoke() = CoroutineScope(Dispatchers.IO).launch { database.clearAllTables() }
}
