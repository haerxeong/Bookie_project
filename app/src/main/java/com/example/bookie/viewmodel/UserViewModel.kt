package com.example.bookie.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookie.repository.UserRepository
import com.example.bookie.User

class UserViewModel : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> get() = _user

    private val repository = UserRepository()
    init {
        val userId = "1" // Replace with actual user ID
        repository.observeUser(userId, _user)
    }
}