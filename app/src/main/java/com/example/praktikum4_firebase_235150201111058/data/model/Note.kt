package com.example.praktikum4_firebase_235150201111058.data.model

data class Note (
    val id : String? = null,
    val title : String = "",
    val content : String = "",
    val timestamp: Long = System.currentTimeMillis()
)