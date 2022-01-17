package com.patofch.todoapp.data.utils

import android.content.Context

fun Context.sarasa(key: String, value: Int) {
    getSharedPreferences("",Context.MODE_PRIVATE)
        .edit()
        .putInt(key, value)
        .apply()
    }
