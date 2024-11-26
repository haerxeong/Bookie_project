package com.example.bookie

import com.google.firebase.database.PropertyName

data class MyBook(
    val id: String = "", // 기본값: 빈 문자열
    val title: String = "", // 기본값: 빈 문자열
    val author: String = "", // 기본값: 빈 문자열
    val publisher: String = "", // 기본값: 빈 문자열
    val release: Int = 0, // 기본값: 0
    //val을 사용한 불변 프로퍼티에는 @set: 어노테이션을 적용할 수 없기 때문입니다.
    //Setter는 var(가변 프로퍼티)에서만 존재하므로, @set:PropertyName을 적용하려면 val 대신 var를 사용해야 합니다.
    @get:PropertyName("isRead") @set:PropertyName("isRead")
    var isRead: Boolean = false, // 기본값: false  // Getter와 Setter를 isRead로 명시
   // val profileImageUrl: String = "", // 기본값: 빈 문자열
    val bookImageUrl: String = "" // 기본값: 빈 문자열
    //val reviewText: String = "" // 기본값: 빈 문자열
)
