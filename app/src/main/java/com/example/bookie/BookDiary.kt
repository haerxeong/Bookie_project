package com.example.bookie

data class BookDiary(
    val readDate: String = "", //읽은날짜를 String or Int 중 뭘로 해야하나
    val reviewText: String = "", // 기본값: 빈 문자
    val bookName: String = ""
)
