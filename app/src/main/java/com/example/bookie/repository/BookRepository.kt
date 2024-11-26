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

    // **Create**: 새로운 책 데이터를 추가
    fun addBook(userId: String, book: MyBook) {
        val bookId = userRef.child(userId).child("booklist").push().key // 고유 ID 생성
        bookId?.let {
            val bookWithId = book.copy(id = it) // ID가 포함된 책 객체 생성
            userRef.child(userId).child("booklist").child(it).setValue(bookWithId)
        }
    }

    // **Read**: 특정 책 데이터를 가져오는 함수
    fun getBook(userId: String, bookId: String, bookLiveData: MutableLiveData<MyBook>) {
        userRef.child(userId).child("booklist").child(bookId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 책이 존재할 경우
                if (snapshot.exists()) {
                    val book = MyBook(
                        id = snapshot.child("id").getValue(String::class.java) ?: "",
                        title = snapshot.child("title").getValue(String::class.java) ?: "",
                        author = snapshot.child("author").getValue(String::class.java) ?: "",
                        publisher = snapshot.child("publisher").getValue(String::class.java) ?: "",
                        release = snapshot.child("release").getValue(Int::class.java) ?: 0,
                        isRead = snapshot.child("isRead").getValue(Boolean::class.java) ?: false,
                        bookImageUrl = snapshot.child("bookImageUrl").getValue(String::class.java) ?: ""
                    )
                    bookLiveData.postValue(book) // 책 데이터를 LiveData로 전달
                } else {
                    println("Book not found.")
                    bookLiveData.postValue(null) // 책을 찾지 못한 경우 null 전달
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read book: ${error.message}")
            }
        })
    }

    // **Read**: 책 목록을 관찰하여 LiveData로 반환
    fun observeBookList(userId: String, bookListLiveData: MutableLiveData<List<MyBook>>) {
        userRef.child(userId).child("booklist").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val bookList = mutableListOf<MyBook>()
                for (data in snapshot.children) {
                    val book = data.getValue(MyBook::class.java)
                    // Filter out invalid entries
                    if (book != null && book.id.isNotEmpty() && book.title.isNotEmpty()) {
                        bookList.add(book)
                    }
                }
                bookListLiveData.postValue(bookList) // Update LiveData with valid books
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to load books: ${error.message}")
            }
        })
    }

    // **Update**: 특정 책 데이터를 업데이트
    fun updateBook(userId: String, bookId: String, updatedBook: MyBook) {
        userRef.child(userId).child("booklist").child(bookId).setValue(updatedBook)
    }

    // **Delete**: 특정 책 데이터를 삭제
    fun deleteBook(userId: String, bookId: String) {
        userRef.child(userId).child("booklist").child(bookId).removeValue()
    }
}
