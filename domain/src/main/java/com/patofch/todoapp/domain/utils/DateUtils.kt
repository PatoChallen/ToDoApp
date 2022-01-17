package com.patofch.todoapp.domain.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getCurrentDate(): Date = Calendar.getInstance().time

fun Date.toDateString(): String = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault()).format(this)

fun String.toDate(): Date = SimpleDateFormat("dd/M/yyyy hh:mm:ss", Locale.getDefault()).parse(this) ?: Date()