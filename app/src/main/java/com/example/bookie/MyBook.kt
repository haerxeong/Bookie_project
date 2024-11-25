package com.example.bookie

data class MyBook(
    val id: String = "", // 기본값: 빈 문자열
    val title: String = "", // 기본값: 빈 문자열
    val author: String = "", // 기본값: 빈 문자열
    val publisher: String = "", // 기본값: 빈 문자열
    val release: Int = 0, // 기본값: 0
    val isRead: Boolean = false, // 기본값: false
   // val profileImageUrl: String = "", // 기본값: 빈 문자열
    val bookImageUrl: String = "", // 기본값: 빈 문자열
    //val reviewText: String = "" // 기본값: 빈 문자열
)
