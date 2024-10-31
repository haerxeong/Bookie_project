package com.example.bookie



data class Feeds(
    val idImage: Int, // 프로필 이미지 리소스 ID
    val name: String,
    val bookName: String,
    val bookReview: String,
    val bookImage: Int // 책 이미지 리소스 ID
)
