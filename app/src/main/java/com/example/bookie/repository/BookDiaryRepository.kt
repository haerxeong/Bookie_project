package com.example.bookie.repository

import androidx.lifecycle.MutableLiveData
import com.example.bookie.BookDiary
import com.example.bookie.MyBook
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class BookDiaryRepository {
    private val database = Firebase.database
    private val userRef = database.getReference("users")

    // **Create**: 새로운 다이어리 데이터를 추가
    fun addDiary(userId: String, bookId: String, diary: BookDiary) {
        userRef.child(userId).child("diaries").child(bookId).setValue(diary)
    }

    // **Read**: 다이어리 포스트? 목록을 관찰하여 LiveData로 반환
    fun observeDiaryList(userId: String, bookDiaryLiveData: MutableLiveData<List<BookDiary>>) {
        userRef.child(userId).child("posts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val diaryList = mutableListOf<BookDiary>()
                for (data in snapshot.children) {
                    val diary = BookDiary(
                        readDate = data.child("timestamp").getValue(String::class.java) ?: "",
                        reviewText = data.child("content").getValue(String::class.java) ?: ""
                    )
                    diaryList.add(diary)
                }
                bookDiaryLiveData.postValue(diaryList)
            }
            override fun onCancelled(error: DatabaseError) {
                // 실패 시 로그 출력 (필요시 MutableLiveData로 에러 상태 전달)
                println("Failed to load books: ${error.message}")
            }
        })
    }

    // **Update**: 특정 다이어리 데이터를 업데이트
    fun updateDiary(userId: String, bookId: String, updatedDiary: BookDiary) {
        userRef.child(userId).child("posts").child(bookId).setValue(updatedDiary)
    }

    // **Delete**: 특정 다이어리 데이터를 삭제
    fun deleteDiary(userId: String, bookId: String) {
        userRef.child(userId).child("posts").child(bookId).removeValue()
    }

}