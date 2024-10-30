package com.example.bookie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.ActivityUnreadBookBinding

class UnreadBookActivity: AppCompatActivity() {
    val unreadbooks = arrayOf(
        MyBook("불편한 편의점", "김호연", "나무옆의자", 2021),
        MyBook("불편한 편의점2", "김호연", "나무옆의자", 2021)
    )

    lateinit var binding: ActivityUnreadBookBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUnreadBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recUnreadBooks.layoutManager = LinearLayoutManager(this)
        binding.recUnreadBooks.adapter = UnreadBooksAdapter(unreadbooks)
    }

}