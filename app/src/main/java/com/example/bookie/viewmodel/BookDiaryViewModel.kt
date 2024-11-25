package com.example.bookie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookie.BookDiary
import com.example.bookie.repository.BookDiaryRepository

class BookDiaryViewModel : ViewModel() {
    private val repository = BookDiaryRepository()

    // LiveData로 책 목록 관리
    private val _diarylist = MutableLiveData<List<BookDiary>>()
    val diarylist: LiveData<List<BookDiary>> get() = _diarylist

    init {
        // Firebase에서 책 목록을 실시간으로 가져옴
        val userId = "1" // 실제 사용자 ID를 설정해야 합니다.
        repository.observeDiaryList(userId, _diarylist)
    }

    // **Create**: 책 추가 -> 사용자가 작성한 BookDiary 데이터 객체를 파이어베이스에 upload 해주는 코드
    fun addDiary(bookId: String, diary: BookDiary) {
        val userId = "1" // 실제 사용자 ID를 사용해야 합니다.
        repository.addDiary(userId, bookId, diary)
    }


}