package com.example.appmovies.common.util

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: String): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    return formatter.format(parser.parse(date) ?: "")
}