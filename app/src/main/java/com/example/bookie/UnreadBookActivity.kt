package com.example.bookie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bookie.databinding.ActivityUnreadBookBinding

class UnreadBookActivity: AppCompatActivity() {
    val unreadbooks = arrayOf(
        MyBook("불편한 편의점", "김호연", "나무옆의자", 2021),
        MyBook("호밀밭의 파수꾼", "제롬 데이비드 샐린저", "민음사", 2023),
        MyBook("채식주의자", "한강", "창비", 2022),
        MyBook("코스모스", "칼 세이건", "사이언스북스", 2006),
        MyBook("물고기는 존재하지 않는다", "룰루 밀러", "곰출판", 2021)
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