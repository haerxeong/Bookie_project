package com.example.bookie

data class MyBook(
    val id: Int,
    val title: String,
    val author: String,
    val publisher: String,
    val release: Int,
    val isRead: Boolean = false,

    // 진주가 추가함
    //val profileImageRes: Int, //사용자 프로필 이미지 리소스
    //val bookImageRes: Int, //책 이미지 리소스
    val reviewText: String // 리뷰
)