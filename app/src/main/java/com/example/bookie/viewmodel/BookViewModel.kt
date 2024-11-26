package com.example.bookie.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.bookie.MyBook
import com.example.bookie.repository.BookRepository

class BookViewModel : ViewModel() {

    // LiveData로 책 목록 관리
    private val _booklist = MutableLiveData<List<MyBook>>()
    val booklist: LiveData<List<MyBook>> get() = _booklist

    private val repository = BookRepository()
    init {
        // Firebase에서 책 목록을 실시간으로 가져옴
        val userId = "1" // 실제 사용자 ID를 설정해야 합니다.
        repository.observeBookList(userId, _booklist)
    }

    // 읽은 책과 읽지 않은 책을 분리한 LiveData
    val readBooks: LiveData<List<MyBook>> = booklist.map {
        it.filter { book -> book.isRead }
    }

    val unreadBooks: LiveData<List<MyBook>> = booklist.map {
        it.filter { book -> !book.isRead }
    }

    // 이미지 URI를 관리하는 변수
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    // **Create**: 책 추가 -> 사용자가 작성한 MyBook 데이터 객체를 파이어베이스에 upload 해주는 코드
    fun addBook(book: MyBook) {
        val userId = "1" // 실제 사용자 ID를 사용해야 합니다.
        repository.addBook(userId, book)
    }

    // **Read**: 이미 `observeBookList`로 구현됨 (실시간 데이터 관찰)
    //책 한권을 상세보기 할 일이 없으므로 Read기능은 필요없음 생략할게용

    // **Update**: Update book
    private fun updateBook(userId: String = "1", bookId: String, updatedBook: MyBook, attribute: String) { // userId는 임시로 1로 설정
        if (attribute == "isRead") {
            val updatedBookList = _booklist.value?.map {
                if (it.id == bookId) {
                    it.copy(isRead = updatedBook.isRead)
                } else {
                    it
                }
            } ?: emptyList()
            _booklist.value = updatedBookList
            repository.updateBook(userId, bookId, updatedBook)
        }
    }

    // 읽음 상태 변경 함수
    fun setIsRead(userId: String = "1", bookId: String, isRead: Boolean) {
        val bookToUpdate = _booklist.value?.find { it.id == bookId }
        bookToUpdate?.let {
            val updatedBook = it.copy(isRead = isRead) // isRead 상태 업데이트
            updateBook(userId, bookId, updatedBook, "isRead")  // 책 정보 업데이트
        }
    }

    // **Delete**: 책 삭제
    fun deleteBook(bookId: String) {
        val userId = "1" // 실제 사용자 ID를 사용해야 합니다.
        repository.deleteBook(userId, bookId)
    }

    // 이미지 URI 설정
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
}