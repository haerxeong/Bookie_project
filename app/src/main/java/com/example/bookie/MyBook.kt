package com.example.bookie

data class MyBook(
    val id: Int,
    val title: String,
    val author: String,
    val publisher: String,
    val release: Int,
    val isRead: Boolean = false
)