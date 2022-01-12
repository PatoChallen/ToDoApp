package com.patofch.todoapp.domain.model

data class Category(
    val id: Int? = null,
    val name: String,
    val image: String,
    val status: String? = null,
    val species: String? = null,
)
