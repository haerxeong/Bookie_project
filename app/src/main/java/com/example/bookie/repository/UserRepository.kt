package com.example.bookie.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import androidx.lifecycle.MutableLiveData
import com.example.bookie.User

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("users")

    fun observeUser(userId: String, userLiveData: MutableLiveData<User>) {
        userRef.child(userId).child("account").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userLiveData.value = user
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }
}