package com.example.bookie.repository

import androidx.lifecycle.MutableLiveData
import com.example.bookie.MyBook
import com.google.firebase.Firebase
import com.google.firebase.database.database

class BookRepository {
    val database = Firebase.database

    val userId = 1
    val userRef = database.getReference("users/${userId}")

    fun observeBooks(books: MutableLiveData<List<MyBook>>) {
        userRef.child("booklist").get().addOnSuccessListener { snapshot ->
            val value = snapshot.children.mapNotNull { it.getValue<MyBook>() }
            books.value = value
        }
    }
}