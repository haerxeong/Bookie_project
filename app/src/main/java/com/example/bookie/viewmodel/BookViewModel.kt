package com.example.bookie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.bookie.MyBook

class BookViewModel : ViewModel() {
    private val _booklist = MutableLiveData<List<MyBook>>()
    val booklist: LiveData<List<MyBook>>
        get() = _booklist

    val readBooks: LiveData<List<MyBook>> = booklist.map {
        it.filter { book -> book.isRead }
    }

    val unreadBooks: LiveData<List<MyBook>> = booklist.map {
        it.filter { book -> !book.isRead }
    }

    // 더미데이터
    init {
        _booklist.value = listOf(
            MyBook(1, "코틀린 인 액션", "드미트리 제메로프", "에이콘", 2017, true),
            MyBook(2, "프로그래밍 코틀린", "스테판 립프란츠", "한빛미디어", 2019, true),
            MyBook(3, "코틀린을 다루는 기술", "라울-게이브리엘 우르마르", "한빛미디어", 2019),
            MyBook(4, "코틀린으로 배우는 함수형 프로그래밍", "이타마르 로사", "한빛미디어", 2019),
            MyBook(5, "코틀린으로 쇼핑몰 만들기", "김영재", "한빛미디어", 2019)
        )
    }

    fun addBook(book: MyBook) {
        val list = _booklist.value?.toMutableList() ?: mutableListOf()
        list.add(book)
        _booklist.value = list
    }

    fun modifyBook(attribute: String, bookId: Int) {
        if (attribute == "isRead") {
            _booklist.value = _booklist.value?.map {
                if (it.id == bookId) {
                    it.copy(isRead = true)
                } else {
                    it
                }
            }
        }
    }

    fun setIsRead(bookId: Int) {
        modifyBook("isRead", bookId)
    }
}