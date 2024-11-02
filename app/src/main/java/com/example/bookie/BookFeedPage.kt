package com.example.bookie

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bookie.databinding.ActivityBookFeedPageBinding


class BookFeedPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBookFeedPageBinding.inflate(layoutInflater)

        //영역함수 이용하여 Fragment 설정하기!
        supportFragmentManager.beginTransaction().run {
            replace(binding.frmLayout.id, BookFeedFragment())
            commit()
        }
        setContentView(binding.root)
    }
}
