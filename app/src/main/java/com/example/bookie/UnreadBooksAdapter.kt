package com.example.bookie

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.bookie.databinding.ListUnreadbooksBinding

class UnreadBooksAdapter(val unreadbooks: Array<MyBook>): RecyclerView.Adapter<UnreadBooksAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ListUnreadbooksBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    // 렌더링해주는 역할
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(unreadbooks[position])
    }

    // view의 아이템 수
    override fun getItemCount() = unreadbooks.size

    class Holder(private val binding: ListUnreadbooksBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(unreadbook: MyBook) {
            binding.imageView.setImageResource(R.drawable.bookcover1)
            binding.txtTitle.text = unreadbook.title
            binding.txtAuthor.text = unreadbook.author
            binding.txtPublisher.text = unreadbook.publisher
            binding.txtRelease.text = unreadbook.release.toString()

            binding.root.setOnClickListener {
                Toast.makeText(binding.root.context, "제목: ${unreadbook.title} 작가: ${unreadbook.author}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}