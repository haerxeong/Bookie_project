package com.example.bookie.repository

import androidx.lifecycle.MutableLiveData
import com.example.bookie.MyBook
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class BookRepository {

    private val database = Firebase.database
    private val userRef = database.getReference("users")

    // **Create**: 새로운 책 데이터 추가
    fun addBook(userId: String, book: MyBook) {
        //자동 으로 고유 ID를 생성 하여 데이터 중복을 방지! 고유 ID를 사용 -> 각 책 항목을 구별
        val bookId = userRef.child(userId).child("booklist").push().key // 고유 ID 생성
        bookId?.let {
            val bookWithId = book.copy(id = it) // ID가 포함된 MyBook 객체 생성
            userRef.child(userId).child("booklist").child(it).setValue(bookWithId)
        }
    }


    // **Read**: 책 목록을 관찰 하여 LiveData 로 반환
    fun observeBookList(userId: String, bookListLiveData: MutableLiveData<List<MyBook>>) {
        userRef.child(userId).child("booklist").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookList = mutableListOf<MyBook>()
                for (data in snapshot.children) {
                    val book = data.getValue(MyBook::class.java)
                    // 유효 하지 않은 항목을 필터링하여 제외
                    if (book != null && book.id.isNotEmpty() && book.title.isNotEmpty()) {
                        bookList.add(book)
                    }
                }
                bookListLiveData.postValue(bookList) // 유효한 책 목록으로 LiveData 업데이트
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to load books: ${error.message}")
            }
        })
    }

    // **Update**: 특정 책 데이터 업데이트
    fun updateBook(userId: String, bookId: String, updatedBook: MyBook) {
        userRef.child(userId).child("booklist").child(bookId).setValue(updatedBook)
    }

    // **Delete**: 특정 책 데이터 삭제
    fun deleteBook(userId: String, bookId: String) {
        userRef.child(userId).child("booklist").child(bookId).removeValue()
    }
}
