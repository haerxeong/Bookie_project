package com.example.bookie

//BopkFeedPagePragment 안의 recycler view 안의 list

//데이터를 저장할 데이터 클래스 정의
data class Feed (
    val profileImageRes: Int, //사용자 프로필 이미지 리소스
    val userName: String, //사용자 이름
    val bookImageRes: Int, //책 이미지 리소스
    val bookName: String, //책 이름
    val reviewText: String //리뷰
)