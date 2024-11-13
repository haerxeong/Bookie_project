package com.example.bookie.viewmodel

import android.net.Uri
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

    // 이미지 URI를 관리하는 변수 추가
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri


    init {
        _booklist.value = listOf(
            MyBook(1, "코틀린 인 액션", "드미트리 제메로프", "에이콘", 2017, true,"결혼한다면 첫쨰 자식의 이름은 코틀린! 너다"),
            MyBook(2, "프로그래밍 코틀린", "스테판 립프란츠", "한빛미디어", 2019, true,"프로그래밍이란..다시 한번 ..포기할 수 있게 용기를 얻었어요"),
            MyBook(3, "코틀린을 다루는 기술", "라울-게이브리엘 우르마르", "한빛미디어", 2019,false,"이 책을 읽고 코틀린과 친해졌어요"),
            MyBook(4, "코틀린으로 배우는 함수형 프로그래밍", "이타마르 로사", "한빛미디어", 2019,false,"프로그래밍 시간 가는 줄 모르겠다. 눈 감았다 뜨니 한달이 지났어!"),
            MyBook(5, "코틀린으로 쇼핑몰 만들기", "김영재", "한빛미디어", 2019,false,"이 책 덕분에 쇼핑몰 사이트를 만들며 프로그래밍 실력이 향상됨")
        )
    }
    //책 추가 함수
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

    // 이미지 URI 설정
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
}