package com.patofch.todoapp.domain.model

import com.patofch.todoapp.domain.utils.getCurrentDate
import java.util.Date

data class Task(
    val id: Int? = null,
    val parentId: Int? = null,
    val name: String,
    val description: String? = null,
    val categoryId: Int? = null,
    val creationDate: Date = getCurrentDate(),
    val limitDate: Date? = null,
    val subTasks: MutableList<Task> = mutableListOf(),
    val status: TaskStatus = TaskStatus.TODO
) {
    
    enum class TaskStatus {
        TODO,
        IN_PROGRESS,
        DONE
    }

    class TaskBuilder (
        val name: String
    ){
        private var parentId: Int? = null
        private var categoryId: Int? = null
        private var description: String? = null
        private var limitDate: Date? = null

        fun setParentId(parentId: Int?) = apply {
            this.parentId = parentId
        }

        fun setDescription(description: String) = apply {
            this.description = description
        }

        fun setCategoryId(categoryId: Int) = apply {
            this.categoryId = categoryId
        }

        fun setLimitDate(limitDate: Date) = apply {
            this.limitDate = limitDate
        }

        fun build() = Task(
            parentId = parentId,
            categoryId = categoryId,
            name = name,
            description = description,
            limitDate = limitDate
        )
    }
}
